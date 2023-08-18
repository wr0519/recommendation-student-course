package com.rainng.coursesystem.recommend;

import org.apache.hadoop.conf.Configuration;
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

public class MongoDBS2  {
    public static class MyMapperS2 extends Mapper<LongWritable, Document, Text, Text> {
        @Override
        protected void map(LongWritable inkey, Document value, Context context) throws IOException, InterruptedException {
            List<String> keys = new ArrayList<>();
            keys.addAll(value.keySet());
            String key = keys.get(1);
            String[] userandshop = key.split(":");
            Text okey = new Text(userandshop[0]);
            Text ovalue = new Text(userandshop[1]);
            context.write(okey, ovalue);
        }
    }

    public static class MyReducerS2 extends Reducer<Text, Text, Document, NullWritable> {
        Document doc = new Document();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            ArrayList<String> list = new ArrayList<>();
            Map<String, String> map = new TreeMap<>();
            for (Text value:
                 values) {
                list.add(value.toString());
            }
            Integer index = new Integer(0);
            for (String str:
                 list) {
                for (String str1:
                     list) {
                    map.put(index.toString(),str+":"+str1);
                    index++;
                }
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
        conf.set("input", "localhost://moan_course.res");
        conf.set("output", "localhost://moan_course.s2");

        Job job = Job.getInstance(conf);
        job.setJarByClass(MongoDBS2.class);

        job.setMapperClass(MyMapperS2.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(MyReducerS2.class);
        job.setOutputKeyClass(Document.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(MongoDBInputFormat.class);
        job.setOutputFormatClass(MongoDBOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}


