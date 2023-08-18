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

public class MongoDBS5  {
    public static class MyMapperS5 extends Mapper<LongWritable, Document, Text, Text> {
        @Override
        protected void map(LongWritable inkey, Document value, Context context) throws IOException, InterruptedException {
            List<String> keys = new ArrayList<>();
            keys.addAll(value.keySet());
            String key = keys.get(1);
            context.write(new Text(key), new Text(value.get(key).toString()));
        }
    }

    public static class MyReducerS5 extends Reducer<Text, Text, Document, NullWritable> {
        Document doc = new Document();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            HashMap<String, Double> map1 = new HashMap<>();
            HashMap<String, Double> map2 = new HashMap<>();
            Map<String, String> map = new TreeMap<>();
            for (Text value : values) {
                String s = value.toString();
                String[] data = s.split(",");
                if (s.contains("-")){
                    for (String datum : data) {
                        String[] strings = datum.split("-");
                        map1.put(strings[0],Double.parseDouble(strings[1]));
                    }
                }else{
                    for (String datum : data) {
                        String[] strings = datum.split(":");
                        map2.put(strings[0],Double.parseDouble(strings[1]));
                    }
                }
            }
            map1.forEach((k1,v1)->{
                map2.forEach((k2,v2)->{
                    Text oKey = new Text(k1 + ":" + k2);
                    Text oValue = new Text(String.valueOf(v1 * v2));
                        System.out.println("reduce输出---------------------------"+oKey.toString()+oValue.toString());
                        map.put(oKey.toString(),oValue.toString());
                });
            });
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
        conf.set("input", "localhost://moan_course.s4");
        conf.set("output", "localhost://moan_course.s5");

        Job job = Job.getInstance(conf);
        job.setJarByClass(MongoDBS5.class);

        job.setMapperClass(MyMapperS5.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(MyReducerS5.class);
        job.setOutputKeyClass(Document.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(MongoDBInputFormat.class);
        job.setOutputFormatClass(MongoDBOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
