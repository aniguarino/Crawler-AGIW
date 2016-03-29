package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import crawler.io.FileManager;
import crawler.model.ContentsDoc;
import crawler.model.Doc;
import crawler.model.Img;
import crawler.model.Log;
import crawler.model.MongoMethods;

public class CrawlerEngine {

	private String accountKey;
	private String bingUrlPatternDoc = "https://api.datamarket.azure.com/Bing/Search/Web?Query=%s&$skip=%d&$format=JSON";
	private String bingUrlPatternImg = "https://api.datamarket.azure.com/Bing/Search/Image?Query=%s&$skip=%d&$format=JSON";
	private FileManager fileManager = new FileManager();
	private ArrayList<String> names = null;
	private MongoMethods mongo = new MongoMethods();
	private ContentsDoc contentsCrawler = new ContentsDoc();
	
	private String pathLog = "log.txt";

	public final int skipMaxDoc = 0;
	public final int skipMaxImg = 0;
	//public final int queryMaxForAccount = 4900;

	public CrawlerEngine(String accountKey, String inputPath){
		this.accountKey = accountKey;
		this.names = fileManager.readNameFromPath(inputPath);
	}

	public void run() throws JSONException{
		// KEY ENCODED TO ACCESS TO THE BING API
		String accountKeyEnc = Base64.getEncoder().encodeToString((accountKey + ":" + accountKey).getBytes());
		int skip = 0;

		try {
			// ITERATE FOR ALL THE NAMES IN INPUT
			for(String keyword : names){
				// EVITATE THE DOC DUPLICATE WITH THE HASHSET
				HashSet<Doc> docsOfKeyword = new HashSet<Doc>();
				HashSet<Img> imgsOfKeyword = new HashSet<Img>();
				
				skip = 0; // RESET SKIP
				int countDiscarded = 0;

				while(skip <= skipMaxDoc){
					System.out.println("In esecuzione...");
					
					// CREATE THE QUERY
					String queryCurrent = "'" + keyword.toString() + "'"; 
					String keywordEncode = URLEncoder.encode(queryCurrent, Charset.defaultCharset().name());

					// *****QUERY THE DOCUMENT*****
					countDiscarded = crawlDocument(docsOfKeyword, keyword, keywordEncode, skip, accountKeyEnc);
					
					// *****QUERY THE IMAGE*****
					if(skip <= skipMaxImg){
						crawlImage(imgsOfKeyword, keyword, keywordEncode, skip, accountKeyEnc);
					}
					skip += 50;
				}

				// PERSIST ALL DOCUMENTS AND IMAGES
				int countErrorPersistDoc = 0;
				int countErrorPersistImg = 0;
				
				for(Doc doc : docsOfKeyword){
					if(!mongo.persist(doc))
						countErrorPersistDoc++;
				}
				
				for(Img img : imgsOfKeyword){
					if(!mongo.persist(img))
						countErrorPersistImg++;
				}
				
				Calendar calendar = Calendar.getInstance();
				Log log = new Log(keyword, (docsOfKeyword.size()-countErrorPersistDoc), docsOfKeyword.size(), countDiscarded,
						(imgsOfKeyword.size()-countErrorPersistImg), imgsOfKeyword.size(), calendar.getTime());
				
				System.out.println("Persistiti "+log.getTruePersistDoc()+" documenti su "+log.getTrueMaxPersistDoc()+" documenti totali da salvare, per la keyword: "+keyword+"; documenti scartarti (privi di ContentHTML): "+log.getDiscardedDoc());
				System.out.println("Persistite "+log.getTruePersistImg()+" immagini su "+log.getTrueMaxPersistImg()+" immagini totali da salvare, per la keyword: "+keyword);
				
				fileManager.writeFile(pathLog, log.toString()+";\n");
			}
			System.out.println(".\n.\n...Esecuzione Terminata con successo...");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// THIS METHOD MAKE THE CRAWLING OF THE DOCUMENT HTML ABOUT ONE KEYWORD AND RETURN THE NUMBER OF THE DISCARDED DOCUMENT
	private int crawlDocument(HashSet<Doc> docsOfKeyword, String keyword, String keywordEncode, int skip, String accountKeyEnc) throws IOException, JSONException{	
		int countDiscarded = 0;
		String queryBingUrlDoc = String.format(bingUrlPatternDoc, keywordEncode, skip);

		// CREATE THE CONNECTION
		URL urlBingDoc = new URL(queryBingUrlDoc);
		URLConnection connectionDoc = urlBingDoc.openConnection();
		connectionDoc.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
		BufferedReader inDoc = new BufferedReader(new InputStreamReader(connectionDoc.getInputStream()));

		// MANAGE THE RESPONSE
		String inputLineDoc;
		StringBuilder responseDoc = new StringBuilder();

		while ((inputLineDoc = inDoc.readLine()) != null) {
			responseDoc.append(inputLineDoc);
		}
		JSONObject jsonDoc = new JSONObject(responseDoc.toString());
		JSONObject dDoc = jsonDoc.getJSONObject("d");
		JSONArray resultsDoc = dDoc.getJSONArray("results");
		int resultsLengthDoc = resultsDoc.length();

		// TAKE ALL THE DOCUMENT FROM THE RESPONSE
		for (int i = 0; i < resultsLengthDoc; i++) {
			JSONObject aResultDoc = resultsDoc.getJSONObject(i);

			String urlDoc = (String)aResultDoc.get("Url");
			String titleDoc = (String)aResultDoc.get("Title");
			String descriptionDoc = (String)aResultDoc.get("Description");

			//SEARCH CONTENT HTML AND INDEX CONTENT FOR EACH DOCUMENT
			String contentHTMLDoc = contentsCrawler.searchHTML(urlDoc);
			String contentIndexDoc = contentsCrawler.searchIndex(urlDoc);
			if(contentHTMLDoc != ""){
				// MANAGE ONLY THE DOCUMENT WITH CONTENTHTML
				// CREATING THE DOCUMENT OBJECT
				Doc doc = new Doc(keyword, urlDoc, titleDoc, descriptionDoc, contentHTMLDoc, contentIndexDoc);
				docsOfKeyword.add(doc);
			}else{
				countDiscarded++;
			}
		}
		return countDiscarded;
	}

	// THIS METHOD MAKE THE CRAWLING OF THE IMAGE ABOUT ONE KEYWORD
	private void crawlImage(HashSet<Img> imgsOfKeyword, String keyword, String keywordEncode, int skip, String accountKeyEnc) throws IOException, JSONException{
		String queryBingUrlImg = String.format(bingUrlPatternImg, keywordEncode, skip);

		// CREATE THE CONNECTION
		URL urlBingImg = new URL(queryBingUrlImg);
		URLConnection connectionImg = urlBingImg.openConnection();
		connectionImg.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
		BufferedReader inImg = new BufferedReader(new InputStreamReader(connectionImg.getInputStream()));

		// MANAGE THE RESPONSE
		String inputLineImg;
		StringBuilder responseImg = new StringBuilder();

		while ((inputLineImg = inImg.readLine()) != null) {
			responseImg.append(inputLineImg);
		}
		JSONObject jsonImg = new JSONObject(responseImg.toString());
		JSONObject dImg = jsonImg.getJSONObject("d");
		JSONArray resultsImg = dImg.getJSONArray("results");
		int resultsLengthImg = resultsImg.length();

		// TAKE ALL THE DOCUMENT FROM THE RESPONSE
		for (int i = 0; i < resultsLengthImg; i++) {
			JSONObject aResultImg = resultsImg.getJSONObject(i);

			String urlImg = (String)aResultImg.get("MediaUrl");
			String urlSourceImg = (String)aResultImg.get("SourceUrl");
			String titleSourceImg = (String)aResultImg.get("Title");

			// SEARCH CONTENT HTML OF THE SOURCE PAGE FOR EACH IMAGE
			String contentSourceImg = contentsCrawler.searchIndex(urlSourceImg);

			// CREATING THE IMAGE OBJECT
			Img img = new Img(keyword, urlImg, urlSourceImg, titleSourceImg, contentSourceImg);
			imgsOfKeyword.add(img);
		}
	}
}






