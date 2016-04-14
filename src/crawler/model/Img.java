package crawler.model;

public class Img {
	private String keyword;
	private String urlImg;
	private String urlSource;
	private String titleSource;
	private String contentSource;
	
	public Img(String keyword, String urlImg, String urlSource, String titleSource, String contentSource) {
		this.keyword = keyword;
		this.urlImg = urlImg;
		this.urlSource = urlSource;
		this.titleSource = titleSource;
		this.contentSource = contentSource;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public String getUrlSource() {
		return urlSource;
	}

	public void setUrlSource(String urlSource) {
		this.urlSource = urlSource;
	}

	public String getTitleSource() {
		return titleSource;
	}

	public void setTitleSource(String titleSource) {
		this.titleSource = titleSource;
	}

	public String getContentSource() {
		return contentSource;
	}

	public void setContentSource(String contentSource) {
		this.contentSource = contentSource;
	}

	public boolean equals(Img doc){
		if(keyword.equals(doc.getKeyword()) && urlImg.equals(doc.getUrlImg()) && urlSource.equals(doc.getUrlSource()) &&
				titleSource.equals(doc.getTitleSource()) && contentSource.equals(doc.getContentSource()))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Img [keyword=" + keyword + ", urlImg=" + urlImg + ", urlSource=" + urlSource + ", titleSource="
				+ titleSource + ", contentSource=" + contentSource + "]";
	}
}
