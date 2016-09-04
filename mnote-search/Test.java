package com.jiurong.mnote.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Test {
	
	
	public static void main(String[] args) throws ExecutionException, Exception {
		String documentId = "a";
		String paragraphId = "1";
		
		String texts = "哈尔滨工业大学";
		String textsModify = "哈尔滨工业大学（威海）";
		
		List<String> documentIdSearch = new ArrayList<String>();
		documentIdSearch.add("a");
		documentIdSearch.add("b");
		documentIdSearch.add("c");
		
		SearchServiceApi searchIndex = new SearchServiceApi();
		
//		searchIndex.addParagraph(IndexType.PARAGRAPH, documentId, paragraphId, texts, "text", null);
		
//		searchIndex.editParagraph(IndexType.COMMENT, documentId, paragraphId, textsModify, "arrayField", null);
		
		searchIndex.search("哈尔滨威海", documentIdSearch);
		
//		searchIndex.deleteParagraph(documentId, paragraphId);
		
//		searchIndex.deleteDocument(documentId);
		
		searchIndex.closeClient();	
		
	}
}



//Node node = nodeBuilder().node();
//Node node = nodeBuilder().clusterName("yourclustername").node();
//Node node = nodeBuilder().client(true).node();
//Node node = nodeBuilder().clusterName("yourclustername").client(true).node();  
//Client client = node.client(); 


//Settings.Builder settings = Settings.builder().put();  
//Client client = TransportClient.builder().settings(settings).build()  
//                .addTransportAddress(new InetSocketTransportAddress(  
//                        InetAddress.getByName("localhost"), Integer.parseInt("9300")));  
//
// IndexResponse response = client.prepareIndex("comment_index", "comment_ugc", "comment_123674")  
//	    .setSource( XContentFactory.jsonBuilder()  
//	    .startObject()  
//	      .field("author", "569874")  
//	      .field("author_name", "riching")  
//	      .field("mark", 232)  
//	      .field("body", "aa北京不错，但是人太多了")  
//	      .field("createDate", "20130801175520")  
//	      .field("valid", true)  
//	    .endObject())  
//	    .setTTL(8000)  
//	    .execute().actionGet();
//
//System.out.println(response.getId());  
//
//SearchRequestBuilder builder = client.prepareSearch("comment_index").setTypes("comment_ugc").setSearchType(SearchType.DEFAULT).setFrom(0).setSize(100);  
//BoolQueryBuilder qb = QueryBuilders.boolQuery().must(new   QueryStringQueryBuilder("北京").field("body"))  
//    .should(new QueryStringQueryBuilder("但是").field("body"));  
//builder.setQuery(qb);  
//SearchResponse responseSearch = builder.execute().actionGet();  
//System.out.println("  " + responseSearch);  
//System.out.println(responseSearch.getHits().getTotalHits()); 
//
//client.close();
