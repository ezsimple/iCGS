package net.ion.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(indexName="chat", type="messages", shards = 1, replicas = 0, refreshInterval = "-1")
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	@Id String id;  // auto generated rows id
	private String who;
	private String message;
	private Date date;
	private String path;
	
	public Message create(String who, String message, String path) {
		this.who = who;
		this.message = message;
		this.date = new Date();
		this.path = path;
		return this;
	}
	
}