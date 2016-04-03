package crawler.model;

public class Category {
	String name;
	Double relevance;
	
	public Category(String name, Double relevance){
		this.name = name;
		this.relevance = relevance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRelevance() {
		return relevance;
	}

	public void setRelevance(Double relevance) {
		this.relevance = relevance;
	}
}
