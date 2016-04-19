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
	MongoCollection<Document> collection_doc;
	MongoCollection<Document> collection_img;
	String ipDb;

	public MongoMethods(String ipDb) {
		this.ipDb = ipDb;
		this.connection = new MongoConnection(ipDb);
		this.mongoClient = MongoConnection.getMongoClient();
		this.database = mongoClient.getDatabase("mydb");
		this.collection_doc = database.getCollection("documenti");
		this.collection_img = database.getCollection("immagini");
	}

	// PERSIST DOCUMENTS
	public boolean persist(Doc document) {
		try{
			Document doc = new Document("Keyword", document.getKeyword().toString())
					.append("URL", document.getUrl().toString())
					.append("Title", document.getTitle().toString())
					.append("Description", document.getDescription().toString())
					.append("ContentHTML", document.getContentHTML().toString())
					.append("ContentIndex", document.getContentIndex().toString())
					.append("Category", document.getCategory().toString());
			collection_doc.insertOne(doc);
			return true;
		}
		catch (Exception exc) {
			System.out.println(exc);
			System.out.println("Il documento con keyword "+ document.getKeyword() +" non e' stato persistito!");
			return false;
		}
	}
	

	// PERSIST IMAGES
	public boolean persist(Img img) {
		try{
			Document image = new Document("Keyword", img.getKeyword().toString())
					.append("URLImg", img.getUrlImg().toString())
					.append("URLSource", img.getUrlSource().toString())
					.append("TitleSource", img.getTitleSource().toString())
					.append("ContentSource", img.getContentSource().toString());
			collection_img.insertOne(image);
			return true;
		}
		catch (Exception exc) {
			System.out.println(exc);
			System.out.println("L'immagine con keyword "+ img.getKeyword() +" non e' stata persistita!");
			return false;
		}
	}

	public Long countDocs(){
		return collection_doc.count();
	}

	public boolean deleteAllDocs(){
		try{
			collection_doc.drop();
			return true;
		}
		catch (Exception exc) {
			System.out.println(exc);
			return false;
		}
	}

	public Doc getDocbyUrl(String url){
		try{
			Document myDoc = collection_doc.find(eq("URL", url)).first();
			Doc doc = new Doc(myDoc.getString("Keyword"),myDoc.getString("URL"),
					myDoc.getString("Title"),myDoc.getString("Description"),
					myDoc.getString("ContentHTML"),myDoc.getString("ContentIndex"),myDoc.getString("Category"));
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
			Document myDoc = collection_doc.find(query).first();
			Doc doc = new Doc(myDoc.getString("Keyword"),myDoc.getString("URL"),
					myDoc.getString("Title"),myDoc.getString("Description"),
					myDoc.getString("ContentHTML"),myDoc.getString("ContentIndex"),myDoc.getString("Category"));
			return doc;
		}	
		catch(Exception exc) {
			System.out.println("Documento non trovato!");
			return null;
		}
	}

	public Long countImgs(){
		return collection_img.count();
	}

	public boolean deleteAllImgs(){
		try{
			collection_img.drop();
			return true;
		}
		catch (Exception exc) {
			System.out.println(exc);
			return false;
		}
	}

	public Img getImgbyUrlImg(String urlImg){
		try{
			Document myImg = collection_img.find(eq("URL_Img", urlImg)).first();
			Img img = new Img(myImg.getString("Keyword"),myImg.getString("URLImg"),
					myImg.getString("URLSource"),myImg.getString("TitleSource"),
					myImg.getString("ContentSource"));
			return img;
		}	
		catch(Exception exc) {
			System.out.println("Immagine non trovata!");
			return null;
		}
	}

	public Img getImgbyUrlSource(String urlSource){
		try{
			Document myImg = collection_img.find(eq("URL_Sorgente", urlSource)).first();
			Img img = new Img(myImg.getString("Keyword"),myImg.getString("URLImg"),
					myImg.getString("URLSource"),myImg.getString("TitleSource"),
					myImg.getString("ContentSource"));
			return img;
		}	
		catch(Exception exc) {
			System.out.println("Immagine non trovata!");
			return null;
		}
	}

	public Img getImgbyKeywordandUrlImg(String keyword, String urlImg){
		try{
			Document query = new Document();
			query.append("Keyword", keyword);
			query.append("URL_Img", urlImg);
			Document myImg = collection_img.find(query).first();
			Img img = new Img(myImg.getString("Keyword"),myImg.getString("URLImg"),
					myImg.getString("URLSource"),myImg.getString("TitleSource"),
					myImg.getString("ContentSource"));
			return img;
		}	
		catch(Exception exc) {
			System.out.println("Immagine non trovata!");
			return null;
		}
	}
}


