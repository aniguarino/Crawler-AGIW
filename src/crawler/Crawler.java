package crawler;

public class Crawler {
    public static void main(final String[] args) throws Exception {
    	
    	final String accountKey = "Cerchiamo di non pubblicare le nostre key =)";
    	final String pathInput = "persone.txt";
    	
		CrawlerEngine crawlerEngine = new CrawlerEngine(accountKey, pathInput);
		
		crawlerEngine.run();
    }
}