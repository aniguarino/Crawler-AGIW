package crawler.model;

public class Log {
	private String keyword;
	private int truePersistDoc;
	private int trueMaxPersistDoc;
	private int discardedDoc;
	private int discardedImg;
	private int truePersistImg;
	private int trueMaxPersistImg;
	private String timestamp;
	
	public Log(String keyword, int truePersistDoc, int trueMaxPersistDoc, int discardedDoc, int discardedImg, int truePersistImg, int trueMaxPersistImg, String timestamp) {
		this.keyword = keyword;
		this.truePersistDoc = truePersistDoc;
		this.trueMaxPersistDoc = trueMaxPersistDoc;
		this.discardedDoc = discardedDoc;
		this.discardedImg = discardedImg;
		this.truePersistImg = truePersistImg;
		this.trueMaxPersistImg = trueMaxPersistImg;
		this.timestamp = timestamp;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getTruePersistDoc() {
		return truePersistDoc;
	}

	public void setTruePersistDoc(int truePersistDoc) {
		this.truePersistDoc = truePersistDoc;
	}

	public int getTrueMaxPersistDoc() {
		return trueMaxPersistDoc;
	}

	public void setTrueMaxPersistDoc(int trueMaxPersistDoc) {
		this.trueMaxPersistDoc = trueMaxPersistDoc;
	}

	public int getDiscardedDoc() {
		return discardedDoc;
	}

	public void setDiscardedDoc(int discardedDoc) {
		this.discardedDoc = discardedDoc;
	}

	public int getTruePersistImg() {
		return truePersistImg;
	}

	public void setTruePersistImg(int truePersistImg) {
		this.truePersistImg = truePersistImg;
	}

	public int getTrueMaxPersistImg() {
		return trueMaxPersistImg;
	}

	public void setTrueMaxPersistImg(int trueMaxPersistImg) {
		this.trueMaxPersistImg = trueMaxPersistImg;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getDiscardedImg() {
		return discardedImg;
	}

	public void setDiscardedImg(int discardedImg) {
		this.discardedImg = discardedImg;
	}

	@Override
	public String toString() {
		return "Log [keyword=" + keyword + ", truePersistDoc=" + truePersistDoc + ", trueMaxPersistDoc="
				+ trueMaxPersistDoc + ", discardedDoc=" + discardedDoc + ", discardedImg=" + discardedImg
				+ ", truePersistImg=" + truePersistImg + ", trueMaxPersistImg=" + trueMaxPersistImg + ", timestamp="
				+ timestamp + "]";
	}
}
