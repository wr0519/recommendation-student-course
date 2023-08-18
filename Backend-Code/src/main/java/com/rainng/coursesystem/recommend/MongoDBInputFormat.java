package com.rainng.coursesystem.recommend;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.*;
import org.bson.Document;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MongoDBInputFormat  extends InputFormat <LongWritable, Document>{

    public MongoDBInputFormat(){}
    //自定义切片类
    public static class MongoDBInputSplit extends InputSplit implements Writable{
        private long start;
        private long end;
        public MongoDBInputSplit(){}
        public MongoDBInputSplit(long start, long end) {
            this.start = start;
            this.end = end;
        }
        @Override
        public void write(DataOutput out) throws IOException {
            out.writeLong(start);
            out.writeLong(end);
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            this.start = in.readLong();
            this.end = in.readLong();
        }

        @Override
        public long getLength() throws IOException, InterruptedException {
            return this.end-this.start;
        }

        @Override
        public String[] getLocations() throws IOException, InterruptedException {
            return new String[0];
        }
    }
    @Override
    public List<InputSplit> getSplits(JobContext context) throws IOException, InterruptedException {
        //获取mongodb的连接
        String url = context.getConfiguration().get("input");
        String[] url_s = url.split("://");
        String dbName = url_s[1].split("\\.")[0];
        String collectionName = url_s[1].split("\\.")[1];

        MongoClient client = new MongoClient(url_s[0],27017);
        MongoDatabase db = client.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        //获取collection的总记录数
        long count = collection.count();
        //定义分片大小
        long chunk = 2;
        //计算分片个数
        long chunksize = (count / chunk);
        //定义存储分片的集合
        List<InputSplit> list = new ArrayList<InputSplit>();
        //循环分片，一个分片chunk条数据
        for (int i = 0; i < chunksize; i++) {
            MongoDBInputSplit mis = null;
            if(i+1 == chunksize){
                mis = new MongoDBInputSplit(i*chunk, count);
                list.add(mis);
            } else {
                mis = new MongoDBInputSplit(i*chunk, i*chunk + chunk);
                list.add(mis);
            }
        }
        return list;
    }

    @Override
    public RecordReader<LongWritable, Document> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return new MongoDBRecordReader(split, context);
    }
    public  static class MongoDBRecordReader extends RecordReader<LongWritable, Document>{
        private MongoDBInputSplit split;
        //结果集
        private MongoCursor<Document> dbcursor;
        //索引，每次都会被初始化成0，只读取当前切片中的k,v
        private int index;
        //偏移量，再下面会自动封装成切片数据的开始，就会知道读多少行 ，对应map泛型的第一个值
        private LongWritable key;
        //每次读到的结果，会通过返回出去，对应  map泛型的第二个
        private Document value;
        //数据库信息
        String ip;
        String dbName;
        String collectionName;

        public MongoDBRecordReader(){}
        public MongoDBRecordReader(InputSplit split,TaskAttemptContext context) throws IOException, InterruptedException{
            super();
            initialize(split, context);

            //获取mongodb的连接
            String url = context.getConfiguration().get("input");
            String[] url_s = url.split("://");
            dbName = url_s[1].split("\\.")[0];
            collectionName = url_s[1].split("\\.")[1];
            ip = url_s[0];
        }
        //初始化，将一些对象new出来，并把得到的切片（1个）强转
        public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
            this.split = (MongoDBInputSplit)split;
            this.key =  new LongWritable();
            this.value = new Document();
        }

        //读取数据，并把数据封装到当前MongoDBRecordReader的k v中
        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException {
            //判断dbcursor是否为null
            if(this.dbcursor == null){
                //获取dbcursor的值
                //获取集合
                MongoClient client = new MongoClient(ip, 27017);
                MongoDatabase db = client.getDatabase(dbName);
                MongoCollection<Document> collection = db.getCollection(collectionName);
                //获取结果集
                dbcursor = collection.find().skip((int) this.split.start).limit((int) this.split.getLength()).iterator();
            }
            //判断
            boolean hasNext = this.dbcursor.hasNext();
            if(hasNext){
                //key
                this.key.set(this.split.start + index);
                index ++;
                //value
                Document next = this.dbcursor.next();
                this.value = next;
            }
            return hasNext;
        }

        @Override
        public LongWritable getCurrentKey() throws IOException, InterruptedException {
            return this.key;
        }

        @Override
        public Document getCurrentValue() throws IOException, InterruptedException {
            return this.value;
        }

        @Override
        public float getProgress() throws IOException, InterruptedException {
            return 0;
        }

        @Override
        public void close() throws IOException {

        }
    }
}

