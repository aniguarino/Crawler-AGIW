package crawler.model;

public class Doc {
	private String keyword;
	private String url;
	private String title;
	private String description;
	private String localPathPage;
	
	public Doc(String keyword, String url, String title, String description, String localPathPage) {
		super();
		this.keyword = keyword;
		this.url = url;
		this.title = title;
		this.description = description;
		this.localPathPage = localPathPage;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocalPathPage() {
		return localPathPage;
	}
	public void setLocalPathPage(String localPathPage) {
		this.localPathPage = localPathPage;
	}
}
