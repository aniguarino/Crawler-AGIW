package crawler.model;

import java.util.Date;

public class Log {
	private String keyword;
	private int truePersistDoc;
	private int trueMaxPersistDoc;
	private int discardedDoc;
	private int truePersistImg;
	private int trueMaxPersistImg;
	private Date timestamp;
	
	public Log(String keyword, int truePersistDoc, int trueMaxPersistDoc, int discardedDoc, int truePersistImg, int trueMaxPersistImg, Date timestamp) {
		this.keyword = keyword;
		this.truePersistDoc = truePersistDoc;
		this.trueMaxPersistDoc = trueMaxPersistDoc;
		this.discardedDoc = discardedDoc;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Log [keyword=" + keyword + ", truePersistDoc=" + truePersistDoc + ", trueMaxPersistDoc="
				+ trueMaxPersistDoc + ", discardedDoc=" + discardedDoc + ", truePersistImg=" + truePersistImg
				+ ", trueMaxPersistImg=" + trueMaxPersistImg + ", timestamp=" + timestamp.toString() + "]";
	}
}
