package com.rainng.coursesystem.recommend;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rainng.coursesystem.dao.AdminDAO;
import com.rainng.coursesystem.dao.StudentLikeDAO;
import com.rainng.coursesystem.model.entity.StudentLikeEntity;
import com.rainng.coursesystem.service.student.CourseLikeService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.log4j.BasicConfigurator;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendModel {

    public static void runRecommend() throws Exception {
    //MongoDBTest的mapreduce执行，计算用户喜好值
        BasicConfigurator.configure();
        Configuration conf = new Configuration();
        conf.set("input","localhost://moan_course.rc_log");
        conf.set("output","localhost://moan_course.res");
        Job job = Job.getInstance(conf);
        job.setJarByClass(MongoDBTest.class);
        job.setMapperClass(MongoDBTest.MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(MongoDBTest.MyReducer.class);
        job.setOutputKeyClass(Document.class);
        job.setOutputValueClass(NullWritable.class);
        job.setInputFormatClass(MongoDBInputFormat.class);
        job.setOutputFormatClass(MongoDBOutputFormat.class);
        job.waitForCompletion(true);
    //MongoDBS1执行
        BasicConfigurator.configure();
        Configuration conf1 = new Configuration();
        conf1.set("input", "localhost://moan_course.res");
        conf1.set("output", "localhost://moan_course.s1");
        Job job1 = Job.getInstance(conf1);
        job1.setJarByClass(MongoDBS1.class);
        job1.setMapperClass(MongoDBS1.MyMapperS1.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(Text.class);
        job1.setReducerClass(MongoDBS1.MyReducerS1.class);
        job1.setOutputKeyClass(Document.class);
        job1.setOutputValueClass(NullWritable.class);
        job1.setInputFormatClass(MongoDBInputFormat.class);
        job1.setOutputFormatClass(MongoDBOutputFormat.class);
        job1.waitForCompletion(true);
    //MongoDBS2执行
        BasicConfigurator.configure();
        Configuration conf2 = new Configuration();
        conf2.set("input", "localhost://moan_course.res");
        conf2.set("output", "localhost://moan_course.s2");
        Job job2 = Job.getInstance(conf2);
        job2.setJarByClass(MongoDBS2.class);
        job2.setMapperClass(MongoDBS2.MyMapperS2.class);
        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(Text.class);
        job2.setReducerClass(MongoDBS2.MyReducerS2.class);
        job2.setOutputKeyClass(Document.class);
        job2.setOutputValueClass(NullWritable.class);
        job2.setInputFormatClass(MongoDBInputFormat.class);
        job2.setOutputFormatClass(MongoDBOutputFormat.class);
        job2.waitForCompletion(true);
    //MongoDBS3执行
        BasicConfigurator.configure();
        Configuration conf3 = new Configuration();
        conf3.set("input", "localhost://moan_course.s2");
        conf3.set("output", "localhost://moan_course.s3");
        Job job3 = Job.getInstance(conf3);
        job3.setJarByClass(MongoDBS3.class);
        job3.setMapperClass(MongoDBS3.MyMapperS3.class);
        job3.setMapOutputKeyClass(Text.class);
        job3.setMapOutputValueClass(IntWritable.class);
        job3.setReducerClass(MongoDBS3.MyReducerS3.class);
        job3.setOutputKeyClass(Document.class);
        job3.setOutputValueClass(NullWritable.class);
        job3.setInputFormatClass(MongoDBInputFormat.class);
        job3.setOutputFormatClass(MongoDBOutputFormat.class);
        job3.waitForCompletion(true);
    //MongoDBS4执行
        BasicConfigurator.configure();
        Configuration conf4 = new Configuration();
        conf4.set("input", "localhost://moan_course.s3");
        conf4.set("output", "localhost://moan_course.s4");
        Job job4 = Job.getInstance(conf4);
        job4.setJarByClass(MongoDBS4.class);
        job4.setMapperClass(MongoDBS4.MyMapperS4.class);
        job4.setMapOutputKeyClass(Text.class);
        job4.setMapOutputValueClass(Text.class);
        job4.setReducerClass(MongoDBS4.MyReducerS4.class);
        job4.setOutputKeyClass(Document.class);
        job4.setOutputValueClass(NullWritable.class);
        job4.setInputFormatClass(MongoDBInputFormat.class);
        job4.setOutputFormatClass(MongoDBOutputFormat.class);
        job4.waitForCompletion(true);
    //MongoDBS5_pre执行
        BasicConfigurator.configure();
        Configuration conf5_pre = new Configuration();
        conf5_pre.set("input", "localhost://moan_course.s1");
        conf5_pre.set("output", "localhost://moan_course.s4");
        Job job5_pre = Job.getInstance(conf5_pre);
        job5_pre.setJarByClass(MongoDBS5_pre.class);
        job5_pre.setMapperClass(MongoDBS5_pre.MyMapperS5_pre.class);
        job5_pre.setMapOutputKeyClass(Text.class);
        job5_pre.setMapOutputValueClass(Text.class);
        job5_pre.setReducerClass(MongoDBS5_pre.MyReducerS5_pre.class);
        job5_pre.setOutputKeyClass(Document.class);
        job5_pre.setOutputValueClass(NullWritable.class);
        job5_pre.setInputFormatClass(MongoDBInputFormat.class);
        job5_pre.setOutputFormatClass(MongoDBOutputFormat.class);
        job5_pre.waitForCompletion(true);
    //MongoDBS5执行
        BasicConfigurator.configure();
        Configuration conf5 = new Configuration();
        conf5.set("input", "localhost://moan_course.s4");
        conf5.set("output", "localhost://moan_course.s5");
        Job job5 = Job.getInstance(conf5);
        job5.setJarByClass(MongoDBS5.class);
        job5.setMapperClass(MongoDBS5.MyMapperS5.class);
        job5.setMapOutputKeyClass(Text.class);
        job5.setMapOutputValueClass(Text.class);
        job5.setReducerClass(MongoDBS5.MyReducerS5.class);
        job5.setOutputKeyClass(Document.class);
        job5.setOutputValueClass(NullWritable.class);
        job5.setInputFormatClass(MongoDBInputFormat.class);
        job5.setOutputFormatClass(MongoDBOutputFormat.class);
        job5.waitForCompletion(true);
    //MongoDBS6执行
        BasicConfigurator.configure();
        Configuration conf6 = new Configuration();
        conf6.set("input", "localhost://moan_course.s5");
        conf6.set("output", "localhost://moan_course.s6");
        Job job6 = Job.getInstance(conf6);
        job6.setJarByClass(MongoDBS6.class);
        job6.setMapperClass(MongoDBS6.MyMapperS6.class);
        job6.setMapOutputKeyClass(Text.class);
        job6.setMapOutputValueClass(Text.class);
        job6.setReducerClass(MongoDBS6.MyReducerS6.class);
        job6.setOutputKeyClass(Document.class);
        job6.setOutputValueClass(NullWritable.class);
        job6.setInputFormatClass(MongoDBInputFormat.class);
        job6.setOutputFormatClass(MongoDBOutputFormat.class);
        job6.waitForCompletion(true);
    //MongoDBS7_pre执行
        BasicConfigurator.configure();
        Configuration conf7_pre = new Configuration();
        conf7_pre.set("input", "localhost://moan_course.rc_log");
        conf7_pre.set("output", "localhost://moan_course.s6");
        Job job7_pre = Job.getInstance(conf7_pre);
        job7_pre.setJarByClass(MongoDBS6.class);
        job7_pre.setMapperClass(MongoDBS7_pre.MyMapperS7_pre.class);
        job7_pre.setMapOutputKeyClass(Text.class);
        job7_pre.setMapOutputValueClass(IntWritable.class);
        job7_pre.setReducerClass(MongoDBS7_pre.MyReducerS7_pre.class);
        job7_pre.setOutputKeyClass(Document.class);
        job7_pre.setOutputValueClass(NullWritable.class);
        job7_pre.setInputFormatClass(MongoDBInputFormat.class);
        job7_pre.setOutputFormatClass(MongoDBOutputFormat.class);
        job7_pre.waitForCompletion(true);
    //MongoDBS7执行
        BasicConfigurator.configure();
        Configuration conf7 = new Configuration();
        conf7.set("input", "localhost://moan_course.s6");
        conf7.set("output", "localhost://moan_course.selected");
        Job job7 = Job.getInstance(conf7);
        job7.setJarByClass(MongoDBS7.class);
        job7.setMapperClass(MongoDBS7.MyMapperS7.class);
        job7.setMapOutputKeyClass(Text.class);
        job7.setMapOutputValueClass(Text.class);
        job7.setReducerClass(MongoDBS7.MyReducerS7.class);
        job7.setOutputKeyClass(Document.class);
        job7.setOutputValueClass(NullWritable.class);
        job7.setInputFormatClass(MongoDBInputFormat.class);
        job7.setOutputFormatClass(MongoDBOutputFormat.class);
        job7.waitForCompletion(true);
    //MongoDBS8执行
        BasicConfigurator.configure();
        Configuration conf8 = new Configuration();
        conf8.set("input", "localhost://moan_course.selected");
        conf8.set("output", "localhost://moan_course.result");
        Job job8 = Job.getInstance(conf8);
        job8.setJarByClass(MongoDBS8.class);
        job8.setMapperClass(MongoDBS8.MyMapperS8.class);
        job8.setMapOutputKeyClass(Text.class);
        job8.setMapOutputValueClass(Text.class);
        job8.setReducerClass(MongoDBS8.MyReducerS8.class);
        job8.setOutputKeyClass(Document.class);
        job8.setOutputValueClass(NullWritable.class);
        job8.setInputFormatClass(MongoDBInputFormat.class);
        job8.setOutputFormatClass(MongoDBOutputFormat.class);
        job8.waitForCompletion(true);
    }

}
