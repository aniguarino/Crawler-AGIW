package crawler.model;

import com.mongodb.MongoClient;

public class MongoConnection {
	private static MongoConnection instance;
	private static MongoClient mongoClient;

	public MongoConnection() {
		mongoClient = new MongoClient ("localhost", 27017 );
	}

	public static MongoConnection getInstance()	{
		if (instance == null){
			instance = new MongoConnection();
		}
		return instance; 
	}
	
	public static MongoClient getMongoClient() {
		return mongoClient;
	}
	
	public static void close() {
		instance = null;
		mongoClient.close();
	}
}