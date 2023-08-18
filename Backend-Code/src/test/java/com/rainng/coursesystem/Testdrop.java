package com.rainng.coursesystem;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Testdrop {
    public static void main(String[] args) {
        MongoClient mongoclient=new MongoClient("127.0.0.1",27017);
        MongoDatabase db=mongoclient.getDatabase("moan_course");
        db.getCollection("res").drop();
        db.getCollection("s1").drop();
        db.getCollection("s2").drop();
        db.getCollection("s3").drop();
        db.getCollection("s4").drop();
        db.getCollection("s5").drop();
        db.getCollection("s6").drop();
        db.getCollection("selected").drop();
        db.getCollection("result").drop();
        mongoclient.close();
    }
}
