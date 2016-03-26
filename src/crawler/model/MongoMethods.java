package crawler.model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

public class MongoMethods {

	MongoConnection connection;
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;

	public MongoMethods() {
		this.connection = new MongoConnection();
		this.mongoClient = MongoConnection.getMongoClient();
		this.database = mongoClient.getDatabase("mydb");
		this.collection = database.getCollection("documenti");
	}

	public boolean persistDoc (Doc document) {
		try{
			Document doc = new Document("Keyword", document.getKeyword().toString())
					.append("URL", document.getUrl().toString())
					.append("Titolo", document.getTitle().toString())
					.append("Descrizione", document.getDescription().toString())
					.append("Content", document.getContent().toString());
			collection.insertOne(doc);
			return true;
		}
		catch (Exception exc) {
			System.out.println(exc);
			System.out.println("Il documento con keyword "+ document.getKeyword() +" non e' stato persistito!");
			return false;
		}
	}

	public Long countDocs(){
		return collection.count();
	}

	public boolean deleteAllDocs(){
		try{
			collection.drop();
			return true;
		}
		catch (Exception exc) {
			System.out.println(exc);
			return false;
		}
	}

	public Doc getDocbyUrl(String url){
		try{
			Document myDoc = collection.find(eq("URL", url)).first();
			Doc doc = new Doc(myDoc.getString("Keyword"),myDoc.getString("URL"),myDoc.getString("Titolo"),myDoc.getString("Descrizione"),myDoc.getString("Content"));
			return doc;
		}	
		catch(Exception exc) {
			System.out.println("Documento non trovato!");
			return null;
		}
	}

	public Doc getDocbyKeywordandUrl(String keyword, String url){
		try{
			Document query = new Document();
			query.append("Keyword", keyword);
			query.append("URL", url);
			Document myDoc = collection.find(query).first();
			Doc doc = new Doc(myDoc.getString("Keyword"),myDoc.getString("URL"),myDoc.getString("Titolo"),myDoc.getString("Descrizione"),myDoc.getString("Content"));
			return doc;
		}	
		catch(Exception exc) {
			System.out.println("Documento non trovato!");
			return null;
		}
	}
}

	

