package com.jiurong.mnote.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {

	private IndexType indexType;	//索引类型，分为段落索引和评论索引。
	private String documentId;
	private String paragraphId;
	private String texts;	//对应传入建立索引的texts内容，返回的是索引匹配到的text片段。
	private String textType; //标记文本类型，纯文本为text，数组字段为arrayField。
	//例如建立索引时的texts为['段落1','段落2','文本3']，之后通过'段落'进行检索，返回的texts应该为['段落1','段落2']
	private List<String> keywords;	//搜索时匹配到的关键字列表。
	
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
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
	public String toString() {
		Map<String, String> mapIndex = new HashMap<String, String>();
		
		mapIndex.put("documentId", documentId);
		mapIndex.put("paragraphId", paragraphId);
		mapIndex.put("indexType", indexType.toString());
		mapIndex.put("texts", texts);
		mapIndex.put("textType", textType);
		mapIndex.put("keywords", "");
		
		return mapIndex.toString();
	}
	
}
