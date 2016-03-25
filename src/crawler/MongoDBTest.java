package crawler;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import crawler.model.Doc;
import crawler.model.MongoConnection;
import crawler.model.MongoMethods;

public class MongoDBTest {

	public static void main(String[] args) {

		Doc doc = new Doc("QuerydelDOC","www.test2.it","TitoloDoc","DescrizioneDoc","c:user..");
		MongoConnection connection = new MongoConnection();
		MongoClient mongoClient = MongoConnection.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("mydb");
		MongoCollection<Document> collection = database.getCollection("documenti");
		MongoMethods mongoMethods = new MongoMethods();

		/*
		if(mongoMethods.deleteAllDocs(collection)){
			System.out.println("DB svuotato!");
		}else{
			System.out.println("Errore");
		} */
		
		
		boolean persist = mongoMethods.PersistDoc(doc, collection);
		if (persist){
			System.out.println("Documento persistito!\n");
		}else{
			System.out.println("Errore\n");
		}
		
		
		Long count = mongoMethods.countDocs(collection);
		System.out.println("Documenti presenti nel DB: "+count);
		
		
		//System.out.println(mongoMethods.getDocbyUrl("www.test.it", collection).getUrl().toString());
		//System.out.println(mongoMethods.getDocbyKeywordandUrl("QuerydelDOC", "www.test.it", collection).getUrl().toString());
	}
	

}
