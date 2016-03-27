package crawler.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ContentsDoc {
	
	public String searchIndex(String url){
		try{
			Document doc = Jsoup.connect(url).get();
			if(doc.hasText())
				return doc.text();
				
				return ""; //Documento vuoto
			}catch(Exception exc){
				return ""; //Network irraggiungibile
			}
	}
	
	public String searchHTML(String url){
		try{
			Document doc = Jsoup.connect(url).get();
			if(doc.hasText())
				return doc.html();
				
				return ""; //Documento vuoto
			}catch(Exception exc){
				return ""; //Network irraggiungibile
			}
	}	
}