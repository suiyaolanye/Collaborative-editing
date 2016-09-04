package com.jiurong.mnote.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.BasicConfigurator;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.sort.SortParseElement;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

public class SearchServiceApi implements SearchService {
	Client client;
	
	SearchServiceApi() {
//		BasicConfigurator.configure();
		Settings.Builder settings = Settings.builder().put();  
		try {
			client = TransportClient.builder().settings(settings).build()  
			                .addTransportAddress(new InetSocketTransportAddress(  
			                        InetAddress.getByName("localhost"), Integer.parseInt("9300")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}  
	}
	

	public void addParagraph(IndexType indexType, String documentId, String paragraphId, String texts, String textType,
			List<String> keywords) throws IOException {
		if(indexType == null || documentId == null || paragraphId == null || texts == null || textType == null) {
			return;
		}

		IndexResponse response = client.prepareIndex(documentId, paragraphId, documentId + paragraphId)
		.setSource( XContentFactory.jsonBuilder()  
		.startObject()  
		  .field("indexType",indexType)
		  .field("textType",textType)
		  .field("texts", texts)  
		  .field("createDate", new Date())   
		.endObject())  
		.setTTL(8000)  
		.execute().actionGet();
	}

	public void editParagraph(IndexType indexType, String documentId, String paragraphId, String texts, String textType, 
			List<String> keywords) throws IOException, ExecutionException, Exception {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(documentId);
		updateRequest.type(paragraphId);
		updateRequest.id(documentId+paragraphId);

		updateRequest.doc(jsonBuilder()
		        .startObject()
		          .field("indexType",indexType)
		          .field("textType",textType)
				  .field("texts", texts)  
				  .field("createDate", new Date())   
		        .endObject());
		client.update(updateRequest).get();
	
	}

	public List<Index> search(String keyword, Collection<String> documentIds) {
		List<Index> result = new ArrayList<Index>();
		
		SearchRequestBuilder builder;
		if(documentIds == null || documentIds.size()==0) {
			builder = client.prepareSearch().setTypes().setSearchType(SearchType.DEFAULT).setFrom(0).setSize(100);  
		}
		else {
			int size=documentIds.size();  
	        String[] array = (String[])documentIds.toArray(new String[size]); 
			builder = client.prepareSearch(array).setTypes().setSearchType(SearchType.DEFAULT).setFrom(0).setSize(100);  
		}
		
		BoolQueryBuilder qb = QueryBuilders.boolQuery().must(new QueryStringQueryBuilder(keyword).field("texts"))  
		    .should(new QueryStringQueryBuilder(keyword).field("texts")); 
		
		builder.setQuery(qb);  
		SearchResponse responseSearch = builder.execute().actionGet();  
		
		if(responseSearch.getHits().getTotalHits() == 0) {
			return result;
		}
		else {
			Index itemIndex;
			Map<String, Object> itemMap;
			for(SearchHit h :responseSearch.getHits()) {
				itemIndex = new Index();
				itemMap = h.getSource();
				
				itemIndex.setDocumentId(h.getIndex());
				itemIndex.setParagraphId(h.getType());
				itemIndex.setIndexType(IndexType.valueOf((String)itemMap.get("indexType")));
				itemIndex.setTexts((String)itemMap.get("texts"));
				itemIndex.setTextType((String)itemMap.get("textType"));
				
				result.add(itemIndex);
				System.out.println(itemIndex);
			}
		}
//		System.out.println("  " + responseSearch); 
		return result;
	}

	public void deleteParagraph(String documentId, String paragraphId) {
        //默认异步的执行删除		
		DeleteResponse response = client.prepareDelete(documentId, paragraphId, documentId+paragraphId).get();
		
		//false表示在同一线程上执行删除操作
//		DeleteResponse response = client.prepareDelete(documentId, paragraphId, documentId+paragraphId)
//		        .setOperationThreaded(false)
//		        .get();
		
//		if (response.isFound()) {
//            System.out.println("删除成功");
//        } else {
//            System.out.println("删除失败");
//        }	
	}
	
	public void deleteDocument(String documentId) {
		DeleteIndexResponse deleteResponse = client.admin().indices().prepareDelete(documentId)
                .execute().actionGet();
	}
	
	public void closeClient() {
		client.close();
	}

}
