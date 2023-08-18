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

public class MongoDBS1 {
    public static class MyMapperS1 extends Mapper<LongWritable, Document, Text, Text> {
        @Override
        protected void map(LongWritable inkey, Document value, Context context) throws IOException, InterruptedException {
            List<String> keys = new ArrayList<>();
            keys.addAll(value.keySet());
            String key = keys.get(1);
            String[] userandshop = key.split(":");
            Text okey = new Text(userandshop[1]);
            Text ovalue = new Text(userandshop[0] + "-" + value.get(key));
            System.out.println(okey + ":" + ovalue);
            context.write(okey, ovalue);
        }
    }

        public static class MyReducerS1 extends Reducer<Text, Text, Document, NullWritable> {
            Document doc = new Document();

            @Override
            protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
                System.out.println("reduce start.................................................................");
                String oValue = "";
                Map<String, String> map = new TreeMap<>();
                for (Text value:
                     values) {
                    oValue += value.toString()+",";
                    System.out.println(key.toString()+oValue);
                    map.put(key.toString(),oValue);
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
            conf.set("output", "localhost://moan_course.s1");

            Job job = Job.getInstance(conf);
            job.setJarByClass(MongoDBS1.class);

            job.setMapperClass(MyMapperS1.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            job.setReducerClass(MyReducerS1.class);
            job.setOutputKeyClass(Document.class);
            job.setOutputValueClass(NullWritable.class);

            job.setInputFormatClass(MongoDBInputFormat.class);
            job.setOutputFormatClass(MongoDBOutputFormat.class);

            System.exit(job.waitForCompletion(true) ? 0 : 1);
        }
}


