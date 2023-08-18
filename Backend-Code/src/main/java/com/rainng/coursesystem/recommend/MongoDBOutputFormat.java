package com.rainng.coursesystem.recommend;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.bson.Document;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoDBOutputFormat extends OutputFormat<Document, NullWritable> {

    @Override
    public RecordWriter<Document, NullWritable> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new MongoDBRecordWriter(context);
    }
    public static class MongoDBRecordWriter extends RecordWriter<Document, NullWritable> {
        public MongoCollection<Document> collection  = null;
        public MongoDBRecordWriter(TaskAttemptContext context){
            //获取mongodb的连接
            String uri = context.getConfiguration().get("output");
            String[] datas = uri.split("://");
            String ip = datas[0];
            String dbsName = datas[1].split("\\.")[0];
            String tableName = datas[1].split("\\.")[1];
            MongoClient client = new MongoClient(ip,27017);
            collection = client.getDatabase(dbsName).getCollection(tableName);
        }
        public void write(Document key, NullWritable value) throws IOException, InterruptedException {
            List<Document> list = new ArrayList<>();
            for(Map.Entry<String, Object> entry : key.entrySet()){
                Document document = new Document();
                document.put(entry.getKey(),entry.getValue());
                list.add(document);
            }
            System.out.println(list);
//            collection.insertOne(new Document(key));
            collection.insertMany(list);
        }
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {

        }
    }
    @Override
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {

    }
    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new FileOutputCommitter(null, context);
    }
}


