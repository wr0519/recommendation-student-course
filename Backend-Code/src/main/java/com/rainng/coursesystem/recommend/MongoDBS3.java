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
import java.util.*;

public class MongoDBS3  {
    public static class MyMapperS3 extends Mapper<LongWritable, Document, Text, IntWritable> {
        @Override
        protected void map(LongWritable inkey, Document value, Context context) throws IOException, InterruptedException {
            List<String> keys = new ArrayList<>();
            keys.addAll(value.keySet());
            String key = keys.get(1);
            String[] shopandshop = value.get(key).toString().split(":");
            if (shopandshop[0].equals(shopandshop[1])){
                return;
            }
            context.write(new Text(value.get(key).toString()), new IntWritable(1));
        }
    }

    public static class MyReducerS3 extends Reducer<Text, IntWritable, Document, NullWritable> {
        Document doc = new Document();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            Integer sum = 0;
            for (IntWritable value:
                 values) {
                sum += 1;
            }
            Map<String, String> map = new TreeMap<>();
            map.put(key.toString(),sum.toString());
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
        conf.set("input", "localhost://moan_course.s2");
        conf.set("output", "localhost://moan_course.s3");

        Job job = Job.getInstance(conf);
        job.setJarByClass(MongoDBS3.class);

        job.setMapperClass(MyMapperS3.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(MyReducerS3.class);
        job.setOutputKeyClass(Document.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(MongoDBInputFormat.class);
        job.setOutputFormatClass(MongoDBOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}



