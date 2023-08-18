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

public class MongoDBS8  {
    public static class MyMapperS8 extends Mapper<LongWritable, Document, Text, Text> {
        @Override
        protected void map(LongWritable inkey, Document value, Context context) throws IOException, InterruptedException {
            List<String> keys = new ArrayList<>();
            keys.addAll(value.keySet());
            String key = keys.get(1);
            if (value.get(key).toString().contains("selected"))
                return;
            context.write(new Text(key.split(":")[0]), new Text(key.split(":")[1]));
        }
    }

    public static class MyReducerS8 extends Reducer<Text, Text, Document, NullWritable> {
        Document doc = new Document();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Map<String, String> map = new TreeMap<>();
            String okey = key.toString();
            String ovalue = "";
            for (Text value:
                    values) {
                ovalue = ovalue + value.toString()+",";
            }
            map.put(okey,ovalue);
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
        conf.set("input", "localhost://moan_course.selected");
        conf.set("output", "localhost://moan_course.result");

        Job job = Job.getInstance(conf);
        job.setJarByClass(MongoDBS8.class);

        job.setMapperClass(MyMapperS8.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(MyReducerS8.class);
        job.setOutputKeyClass(Document.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(MongoDBInputFormat.class);
        job.setOutputFormatClass(MongoDBOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

