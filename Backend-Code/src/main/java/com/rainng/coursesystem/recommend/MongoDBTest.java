package com.rainng.coursesystem.recommend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.BasicConfigurator;
import org.bson.Document;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MongoDBTest {
    public static class MyMapper extends Mapper<LongWritable, Document, Text, IntWritable>{
        @Override
        protected void map(LongWritable key, Document value, Context context) throws IOException, InterruptedException {
            if(value.get("userId")==null){
                return;
            }
            int userId = (int) value.get("userId");
            String requestUrl = (String) value.get("requestUrl");
            String[] data = requestUrl.split("/");
            //计算喜好值
            String ItemId = "";
            int like = 0;
            if (userId == 2){
                System.out.println(userId);
            }
            if (data.length>=6){
                if (data[4].equals("select")&&!data[5].equals("detail")&&!data[5].equals("recommend")&&!data[5].equals("page")){
                    like = 5;
                    ItemId = data[5];
                }
                if (data[5].equals("detail")){
                    like = 3;
                    ItemId = data[6];
                }
            }
            if (ItemId.isEmpty())
                return;
            String outputkey = userId+":"+ItemId;
            System.out.println(outputkey+like);
            context.write(new Text(outputkey), new IntWritable(like));
        }
    }
    public static class MyReducer extends Reducer<Text, IntWritable, Document, NullWritable>{
        Document doc = new Document();
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int num = 0;
            for(IntWritable value:values){
                num+=value.get();
            }
            Map<String, Integer> map = new TreeMap<String, Integer>();
            map.put(key.toString(), num);
            //map.put("aa", num*10);
            doc.putAll(map);
        }
        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            //写入
            if(doc.isEmpty()){
                return;
            }
            context.write(doc, NullWritable.get());
        }
    }
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        Configuration conf = new Configuration();
        conf.set("input","localhost://moan_course.rc_log");
        conf.set("output","localhost://moan_course.res");

        Job job = Job.getInstance(conf);
        job.setJarByClass(MongoDBTest.class);

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Document.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(MongoDBInputFormat.class);
        job.setOutputFormatClass(MongoDBOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

