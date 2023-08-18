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
import java.math.BigDecimal;
import java.util.*;

public class MongoDBS7_pre  {
    public static class MyMapperS7_pre extends Mapper<LongWritable, Document, Text, IntWritable> {
        @Override
        protected void map(LongWritable inkey, Document value, Context context) throws IOException, InterruptedException {
            if(value.get("userId")==null){
                return;
            }
            int userId = (int) value.get("userId");
            String requestUrl = (String) value.get("requestUrl");
            String[] data = requestUrl.split("/");
            //计算喜好值
            String ItemId = "";
            if (data.length>=6){
                if (data[4].equals("select")&&!data[5].equals("recommend")&&!data[5].equals("page")&&!data[5].equals("detail")){
                    ItemId = data[5];
                }
            }
            if (ItemId.isEmpty())
                return;
            String outputkey = userId+"-"+ItemId;
            context.write(new Text(outputkey), new IntWritable(1));
        }
    }

    public static class MyReducerS7_pre extends Reducer<Text, IntWritable, Document, NullWritable> {
        Document doc = new Document();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            Map<String, String> map = new TreeMap<>();
            for (IntWritable value:
                 values) {
                map.put(key.toString(),value.toString());
            }
            doc.putAll(map);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            //写入
            if (doc.isEmpty()) {
                return;
            }
            context.write(doc, NullWritable.get());
        }
    }

    public static void main(String args[]) throws Exception {
        BasicConfigurator.configure();
        Configuration conf = new Configuration();
        conf.set("input", "localhost://moan_course.rc_log");
        conf.set("output", "localhost://moan_course.s6");

        Job job = Job.getInstance(conf);
        job.setJarByClass(MongoDBS6.class);

        job.setMapperClass(MyMapperS7_pre.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(MyReducerS7_pre.class);
        job.setOutputKeyClass(Document.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(MongoDBInputFormat.class);
        job.setOutputFormatClass(MongoDBOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

