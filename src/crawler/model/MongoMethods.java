package crawler.model;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

public class MongoMethods {
	
	public MongoMethods() {
	}
	
	public boolean PersistDoc (Doc document, MongoCollection<Document> collection) {
		try{
			Document doc = new Document("Keyword", document.getKeyword().toString())
					.append("URL", document.getUrl().toString())
					.append("Titolo", document.getTitle().toString())
					.append("Descrizione", document.getDescription().toString())
					.append("Path_Filesystem", document.getLocalPathPage().toString());
			collection.insertOne(doc);
			return true;
		}
		catch (Exception exc) {
		      System.out.println(exc);
		      System.out.println("Il documento con keyword "+ document.getKeyword() +" non e' stato persistito!");
		      return false;
		    }
		}
	
	public Long countDocs(MongoCollection<Document> collection){
		return collection.count();
	}
	
	public boolean deleteAllDocs(MongoCollection<Document> collection){
		try{
			collection.drop();
			return true;
		}
		catch (Exception exc) {
		      System.out.println(exc);
		      return false;
		}
	}
	
	public Doc getDocbyUrl(String url, MongoCollection<Document> collection){
		try{
			Document myDoc = collection.find(eq("URL", url)).first();
			Doc doc = new Doc(myDoc.getString("Keyword"),myDoc.getString("URL"),myDoc.getString("Titolo"),myDoc.getString("Descrizione"),myDoc.getString("Path_Filesystem"));
			return doc;
		}	
		catch(Exception exc) {
			System.out.println("Documento non trovato!");
			return null;
		}
	}
	
	public Doc getDocbyKeywordandUrl(String keyword, String url, MongoCollection<Document> collection){
		try{
			Document query = new Document();
			query.append("Keyword", keyword);
			query.append("URL", url);
			Document myDoc = collection.find(query).first();
			Doc doc = new Doc(myDoc.getString("Keyword"),myDoc.getString("URL"),myDoc.getString("Titolo"),myDoc.getString("Descrizione"),myDoc.getString("Path_Filesystem"));
			return doc;
		}	
		catch(Exception exc) {
			System.out.println("Documento non trovato!");
			return null;
		}
	}

	
	
	
}

	

