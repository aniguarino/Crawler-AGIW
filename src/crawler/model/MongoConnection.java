package crawler.model;

import com.mongodb.MongoClient;

public class MongoConnection {
	private static MongoConnection instance;
	private static MongoClient mongoClient;
	private static String ipDb;

	public MongoConnection(String ipDb) {
		MongoConnection.ipDb = ipDb;
		mongoClient = new MongoClient (ipDb, 27017 );
	}

	public static MongoConnection getInstance()	{
		if (instance == null){
			instance = new MongoConnection(ipDb);
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