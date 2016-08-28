package com.jiurong.mnote.search;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;  
import org.elasticsearch.client.Client;  
import org.elasticsearch.node.Node;

public class Test {
	public static void main(String[] args) {
//		System.out.println("aaa");
		
		Node node = nodeBuilder().node();
//		Node node = nodeBuilder().clusterName("escluster2").client(true).node();  
		Client client = node.client(); 
		
		node.close();
			
	}
}
