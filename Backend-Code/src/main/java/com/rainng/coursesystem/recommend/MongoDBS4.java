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

public class MongoDBS4  {
    public static class MyMapperS4 extends Mapper<LongWritable, Document, Text, Text> {
        @Override
        protected void map(LongWritable inkey, Document value, Context context) throws IOException, InterruptedException {
            List<String> keys = new ArrayList<>();
            keys.addAll(value.keySet());
            String key = keys.get(1);
            String[] shopandshop = key.split(":");
            context.write(new Text(shopandshop[0]), new Text(shopandshop[1]+":"+value.get(key)));
        }
    }

    public static class MyReducerS4 extends Reducer<Text, Text, Document, NullWritable> {
        Document doc = new Document();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            for (Text value:
                 values) {
                sb.append(value.toString()).append(",");
            }
            Map<String, String> map = new TreeMap<>();
            map.put(key.toString(),sb.toString());
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
        conf.set("input", "localhost://moan_course.s3");
        conf.set("output", "localhost://moan_course.s4");

        Job job = Job.getInstance(conf);
        job.setJarByClass(MongoDBS4.class);

        job.setMapperClass(MyMapperS4.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(MyReducerS4.class);
        job.setOutputKeyClass(Document.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(MongoDBInputFormat.class);
        job.setOutputFormatClass(MongoDBOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}



