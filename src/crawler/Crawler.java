package crawler;

public class Crawler {
    public static void main(final String[] args) throws Exception {
    	
    	final String accountKey = "qblXAUVW0S6ewlnMwwUwKW8euw/SL2+Cs8jF7SLqmjI";
    	final String pathInput = "persone.txt";
    	
		CrawlerEngine crawlerEngine = new CrawlerEngine(accountKey, pathInput);
		
		crawlerEngine.run();
    }
}