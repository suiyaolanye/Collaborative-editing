package com.jiurong.mnote.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {

	private IndexType indexType;	//索引类型，分为段落索引和评论索引。
	private String documentId;
	private String paragraphId;
	private String texts;	        //对应传入建立索引的texts内容，返回的是索引匹配到的text片段。
	private String textType;        //标记文本类型，纯文本为text，数组字段为arrayField。
	private List<String> atWords;	//添加索引时，at后面的人名，便于分词。
	private List<String> highlightText;  //查询结果返回时，匹配到的查询词，高亮表示。
	
	public IndexType getIndexType() {
		return indexType;
	}
	public void setIndexType(IndexType indexType) {
		this.indexType = indexType;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getParagraphId() {
		return paragraphId;
	}
	public void setParagraphId(String paragraphId) {
		this.paragraphId = paragraphId;
	}
	public String getTexts() {
		return texts;
	}
	public void setTexts(String texts) {
		this.texts = texts;
	}
	public String getTextType() {
		return textType;
	}
	public void setTextType(String textType) {
		this.textType = textType;
	}
	public List<String> getAtWords() {
		return atWords;
	}
	public void setAtWords(List<String> atWords) {
		this.atWords = atWords;
	}
	public List<String> getHighlightText() {
		return highlightText;
	}
	public void setHighlightText(List<String> highlightText) {
		this.highlightText = highlightText;
	}
	
	public String toString() {
		Map<String, String> mapIndex = new HashMap<String, String>();
		
		mapIndex.put("documentId", documentId);
		mapIndex.put("paragraphId", paragraphId);
		mapIndex.put("indexType", indexType.toString());
		mapIndex.put("texts", texts);
		mapIndex.put("textType", textType);
		mapIndex.put("atWords", "");
		mapIndex.put("highlightText", getListString(highlightText));
		
		return mapIndex.toString();
	}
	
	private String getListString(List<String> list) {
		StringBuffer str = new StringBuffer("[");
		for(String s: list) {
			str.append(s);
			str.append(",");
		}
		str.append("]");
		return str.toString();
	}
	
}
