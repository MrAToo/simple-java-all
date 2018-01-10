package com.mrdu.simple.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoSimple {
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	
	@Before
	public void getConn(){
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("test");
		System.out.println("MongoDB Connection Successfully...");
	}

	//创建集合
	@Test
	public void createCollection(){
		database.createCollection("col1");
		System.out.println("Collection Create Successfully...");
	}
	
	//获取集合
	@Test
	public void getCollection(){
		MongoCollection<Document> collection = database.getCollection("col1");
		System.out.println(collection);
	}
	
	//插入文档
	@Test
	public void insertDocument(){
		MongoCollection<Document> collection = database.getCollection("col1");
		List<Document> list = new ArrayList<>();
		for(int i=1;i<100;i++){
			Document document = new Document("name","name"+i)
					.append("age", i)
					.append("gender", i/2==0?"男":"女");
			list.add(document);
		}
		collection.insertMany(list);
		System.out.println("MongoDB Insert Successfully...");
	}
	
	//检索文档
	@Test
	public void findDocument(){
		MongoCollection<Document> collection = database.getCollection("col1");
		FindIterable<Document> find = collection.find();
		MongoCursor<Document> iterator = find.iterator();
		while (iterator.hasNext()) {
			Document next = iterator.next();
			System.out.println(next.get("_id"));
			System.out.println(next.get("name"));
			System.out.println(next.get("age"));
			System.out.println(next.get("gender"));
		}
		System.out.println("MongoDB Find Successfully...");
	}
	
	//更新文档
	@Test
	public void updateDocument(){
		MongoCollection<Document> collection = database.getCollection("col1");
		collection.updateOne(Filters.eq("_id", new ObjectId("5a4ddadc56c686014cdaf64c")), new Document("$set",new Document("age","18").append("gender", "男")));
		System.out.println("MongoDB Update Successfully...");
	}
	
	
	//删除文档
	@Test
	public void removeDocument(){
		MongoCollection<Document> collection = database.getCollection("col1");
		collection.deleteMany(Filters.eq("name","dujuncheng"));
		System.out.println("MongoDB Delete Successfully...");
	}
	
	//高级查询
	@Test
	public void seniorFind(){
		MongoCollection<Document> collection = database.getCollection("col1");
		//查询出age大于等于18并且从10开始往后取10个升序排列的元素
		FindIterable<Document> iterable = collection.find(new Document("age",new Document("$gte",18))).sort(new Document("age",1)).skip(10).limit(10);
		MongoCursor<Document> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			Document next = iterator.next();
			System.out.println(next.get("_id"));
			System.out.println(next.get("name"));
			System.out.println(next.get("age"));
			System.out.println(next.get("gender"));
		}
		System.out.println("MongoDB SeniorFind Successfully...");
	}
	
	
	@After
	public void closeConn(){
		mongoClient.close();
	}

}
