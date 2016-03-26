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
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;

import crawler.io.FileManager;
import crawler.model.Doc;
import crawler.model.MongoMethods;

public class CrawlerEngine {

	private String accountKey;
	private String bingUrlPattern = "https://api.datamarket.azure.com/Bing/Search/Web?Query=%s&$skip=%d&$format=JSON";
	private FileManager fileManager = new FileManager();
	private ArrayList<String> names = null;
	private MongoMethods mongo = new MongoMethods();

	public final int skipMax = 100;
	//public final int queryMaxForAccount = 4900;

	public CrawlerEngine(String accountKey, String inputPath){
		this.accountKey = accountKey;
		this.names = fileManager.readNameFromPath(inputPath);
	}

	public void run(){
		String query_current = "";
		int skip = 0;

		try {
			// ITERATE FOR ALL THE NAMES IN INPUT
			for(String name : names){
				// EVITATE THE DOC DUPLICATE WITH THE HASHSET
				HashSet<Doc> docsOfKeyword = new HashSet<Doc>();
				
				while(skip <= skipMax){
					// CREATE THE QUERY
					query_current = "'" + name.toString() + "'"; 
					String query = URLEncoder.encode(query_current, Charset.defaultCharset().name());
					String queryBingUrl = String.format(bingUrlPattern, query, skip);
					String accountKeyEnc = Base64.getEncoder().encodeToString((accountKey + ":" + accountKey).getBytes());
					
					// CREATE THE CONNECTION
					URL urlBing = new URL(queryBingUrl);
					URLConnection connection = urlBing.openConnection();
					connection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

					// MANAGE THE RESPONSE
					String inputLine;
					StringBuilder response = new StringBuilder();
					
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					JSONObject json = new JSONObject(response.toString());
					JSONObject d = json.getJSONObject("d");
					JSONArray results = d.getJSONArray("results");
					int resultsLength = results.length();
					
					// TAKE ALL THE DOCUMENT FROM THE RESPONSE
					for (int i = 0; i < resultsLength; i++) {
						JSONObject aResult = results.getJSONObject(i);
						
						String url = (String)aResult.get("Url");
						String title = (String)aResult.get("Title");
						String description = (String)aResult.get("Description");
						String content = "No content"; // TO IMPLEMENT THE CRAWLING
						
						Doc doc = new Doc(name, url, title, description, content);
						docsOfKeyword.add(doc);
					}
					skip += 50;
				}
				// PERSIST ALL THE DOC
				int countErrorPersist = 0;
				for(Doc doc : docsOfKeyword){
					if(!mongo.persistDoc(doc))
						countErrorPersist++;
				}
				
				System.out.println("Persistiti "+(docsOfKeyword.size()-countErrorPersist)+" documenti su "+docsOfKeyword.size()+" documenti totali sulla keyword: "+name);
			}
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
}


