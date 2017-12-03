package com.autotest.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by z1115 on 11/27/2017.
 */
public class test {

    public static void main(String[] args) {
//
//        MongoClientURI uri = new MongoClientURI("mongodb://10.58.1.26:27018");
//        MongoClient mongoClient = new MongoClient(uri);
//        MongoDatabase database = mongoClient.getDatabase("tech-voucher");
//        MongoCollection<Document> collection = database.getCollection("voucherType");
//        collection.deleteMany(new BasicDBObject("typeCode", java.util.regex.Pattern.compile("TEST")));
//        collection.deleteMany(new BasicDBObject("typeCode", java.util.regex.Pattern.compile("typeCode")));
//        collection.deleteMany(new BasicDBObject("createdDate", null));
//        mongoClient.close();
        assertThat(500, is(500));
    }
}
