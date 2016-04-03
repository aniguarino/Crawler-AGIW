package crawler;
/**
 * Text Classification 1.1 starting client for Java.
 *
 * In order to run this example, the license key must be included in the key variable.
 * If you don't know your key, check your personal area at MeaningCloud (https://www.meaningcloud.com/developer/account/licenses)
 *
 * Once you have the key, edit the parameters and call "javac *.java; java ClassClient"
 *
 * You can find more information at http://www.meaningcloud.com/developer/text-classification/doc/1.1
 *
 * @author     MeaningCloud
 * @contact    http://www.meaningcloud.com 
 * @copyright  Copyright (c) 2015, MeaningCloud LLC All rights reserved.
 */
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

import crawler.model.Category;
import crawler.model.Post;

import java.io.ByteArrayInputStream;
import org.w3c.dom.*;


/**
 * This class implements a starting client for Text Classification  
 */
public class TextAnalizer {
	// We define the variables needed to call the API
	private String api = "http://api.meaningcloud.com/class-1.1";
	private String key = "";
	private String model = "IPTC_it";  // IPTC_es/IPTC_en/IPTC_fr/IPTC_it/IPTC_ca/EUROVOC_es_ca/BusinessRep_es/BusinessRepShort_es

	public TextAnalizer(String key){
		this.key = key;
	}

	public TextAnalizer() {
	}

	public String getCategory(String txt){
		try {
			ArrayList<Category> listCategory = new ArrayList<Category>();

			Post post = new Post (api);
			post.addParameter("key", key);
			post.addParameter("txt", txt);
			post.addParameter("model", model);
			post.addParameter("of", "xml");
			String response = post.getResponse();

			// Prints the specific fields in the response (categories)
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
			doc.getDocumentElement().normalize();
			Element response_node = doc.getDocumentElement();

			NodeList status_list = response_node.getElementsByTagName("status");
			Node status = status_list.item(0);
			NamedNodeMap attributes = status.getAttributes();
			Node code = attributes.item(0);
			if(code.getTextContent().equals("0")) {
				NodeList category_list = response_node.getElementsByTagName("category_list");
				if(category_list.getLength()>0){
					Node categories = category_list.item(0);          
					NodeList category = categories.getChildNodes();
					for(int i=0; i<category.getLength(); i++) {
						Node info_category = category.item(i);  
						NodeList child_category = info_category.getChildNodes();
						String label = "";
						String relevance = "";
						for(int j=0; j<child_category.getLength(); j++){
							Node n = child_category.item(j);
							String name = n.getNodeName();
							if(name.equals("label"))
								label = n.getTextContent();
							else if(name.equals("relevance"))
								relevance = n.getTextContent();
						}
						Category curr = new Category(label, Double.parseDouble(relevance));
						listCategory.add(curr);
					}
				}
			}
			if(listCategory.isEmpty())
				return "Senza categoria";

			Category max = listCategory.get(0);

			for(Category curr : listCategory){
				if(curr.getRelevance() > max.getRelevance())
					max = curr;
			}

			return max.getName();
		} catch (Exception e) {
			return "Senza categoria";
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
