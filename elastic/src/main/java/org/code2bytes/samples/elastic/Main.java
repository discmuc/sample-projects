package org.code2bytes.samples.elastic;

import java.io.File;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static void main(String[] args) {

		try {
			FileDescription fileDescr = new FileDescription();
			fileDescr.setPath("D:/Temp/");
			fileDescr.setName("tess4j.tif");
			File file = new File(fileDescr.getPath() + fileDescr.getName());
			if (file.exists()) {
				Date lastModified = new Date(file.lastModified());
				fileDescr.setLastModifed(lastModified);
				fileDescr.setTags("Tag1 Tag2");
				fileDescr.setContent("Testcontent ...");
			} else {
				System.exit(-1);
			}

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(fileDescr);

			System.out.println(json);

			Settings settings = ImmutableSettings.settingsBuilder()
					.put("cluster.name", "elasticsearch").build();
			Client client = new TransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(
							"172.21.239.123", 9300));

			System.out.println("Connected to ElasticSearch ...");

			boolean fileExists = true;
			if (!fileExists) {
				IndexResponse response = client.prepareIndex("fs", "file")
						.setSource(json).execute().actionGet();

				System.out.println(response.getIndex() + " : "
						+ response.getType() + " : " + response.getId() + " : "
						+ response.getVersion());

				System.out.println("FileDescr written ...");
			}

			System.out.println("Leave ElasticSearch ...");
			client.close();

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
