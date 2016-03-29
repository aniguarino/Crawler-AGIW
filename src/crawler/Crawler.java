package crawler;

public class Crawler {
    public static void main(final String[] args) throws Exception {
    	
    	final String accountKey = "qblXAUVW0S6ewlnMwwUwKW8euw/SL2+Cs8jF7SLqmjI";
    	final String pathInput = "persone.txt";
    	
    	final int skipDoc = Integer.parseInt(args[0]);
    	final int skipImg = Integer.parseInt(args[1]);
    	
		CrawlerEngine crawlerEngine = new CrawlerEngine(accountKey, pathInput, skipDoc, skipImg);
		
		crawlerEngine.run();
    }
}