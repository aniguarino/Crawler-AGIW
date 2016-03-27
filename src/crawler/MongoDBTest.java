package crawler;

import crawler.model.Doc;
import crawler.model.Img;
import crawler.model.MongoMethods;

public class MongoDBTest {

	public static void main(String[] args) {

		Doc doc = new Doc("QuerydelDOC","www.test.it","TitoloDoc","DescrizioneDoc","HTML", "parole contenute");
		Img img = new Img("Querydell'IMG","www.linkimmagine.it","www.linksorgente.it","Titolosorgente","ContentSorgente");
		MongoMethods mongoMethods = new MongoMethods();

		/*
		if(mongoMethods.deleteAllDocs(collection)){
			System.out.println("DB svuotato!");
		}else{
			System.out.println("Errore");
		} */
		
		
		boolean persist = mongoMethods.persistDoc(doc);
		if (persist){
			System.out.println("Documento persistito!");
		}else{
			System.out.println("Errore\n");
		}
		
		System.out.println("Documenti presenti nel DB: " + mongoMethods.countDocs());
		
		
		boolean persist2 = mongoMethods.persistImg(img);
		if (persist2){
			System.out.println("Immagine persistita!");
		}else{
			System.out.println("Errore\n");
		}
		
		System.out.println("Immagini presenti nel DB: " + mongoMethods.countImgs());
		
		
		//System.out.println(mongoMethods.getDocbyUrl("www.test.it", collection).getUrl().toString());
		//System.out.println(mongoMethods.getDocbyKeywordandUrl("QuerydelDOC", "www.test.it", collection).getUrl().toString());
	}
	

}
