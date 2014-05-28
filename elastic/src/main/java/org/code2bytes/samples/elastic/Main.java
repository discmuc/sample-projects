package org.code2bytes.samples.elastic;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

public class Main {

	public static void main(String[] args) {
		Node node = nodeBuilder().client(true).build();
		Client client = node.client();
		
		System.out.println("Connected to ElasticSearch ...");
		
		client.close();
		node.close();
	}
}
