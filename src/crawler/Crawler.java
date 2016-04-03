package crawler;

import java.util.ArrayList;

import crawler.io.FileManager;

public class Crawler {
    public static void main(final String[] args) throws Exception {
    	FileManager fileManager = new FileManager();
    	ArrayList<String> keys = fileManager.readStringFromPath("keys.txt");
    	
    	final String accountKey = keys.get(0);
    	final String textAnalizerKey = keys.get(1);
    	
    	final String pathInput = "persone.txt";
    	
    	final int skipDoc = Integer.parseInt(args[0]);
    	final int skipImg = Integer.parseInt(args[1]);
    	
		CrawlerEngine crawlerEngine = new CrawlerEngine(accountKey, textAnalizerKey, pathInput, skipDoc, skipImg);
		
		crawlerEngine.run();
    }
}