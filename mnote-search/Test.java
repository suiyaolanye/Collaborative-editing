package com.jiurong.mnote.search;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.BasicConfigurator;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.node.Node;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

public class Test {
	
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
//		String documentId = uuid();
//		String paragraphId = uuid();
		String documentId = "c";
		String paragraphId = "12";
		String text = "久荣科技";
		
		BasicConfigurator.configure();

		try {	 
			Settings.Builder settings = Settings.builder().put();  
			Client client = TransportClient.builder().settings(settings).build()  
			                .addTransportAddress(new InetSocketTransportAddress(  
			                        InetAddress.getByName("localhost"), Integer.parseInt("9300")));  
			
//			 IndexResponse response = client.prepareIndex(documentId, paragraphId)
//				    .setSource( XContentFactory.jsonBuilder()  
//				    .startObject()  
//				      .field("author", "569874")  
//				      .field("body", "aa北京不错，但是人太多了")  
//				      .field("createDate", new Date())  
//				      .field("isArray",false)
//				    .endObject())  
//				    .setTTL(8000)  
//				    .execute().actionGet();
//		
//			System.out.println(response.getId());  
			
			SearchRequestBuilder builder = client.prepareSearch(documentId).setTypes().setSearchType(SearchType.DEFAULT).setFrom(0).setSize(100);  
			BoolQueryBuilder qb = QueryBuilders.boolQuery().must(new   QueryStringQueryBuilder("北京").field("body"))  
			    .should(new QueryStringQueryBuilder("但是").field("body"));  
			builder.setQuery(qb);  
			SearchResponse responseSearch = builder.execute().actionGet();  
			System.out.println("  " + responseSearch);  
			System.out.println(responseSearch.getHits().getTotalHits()); 
			
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}	
		
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
