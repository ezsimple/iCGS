package net.ion.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(indexName="chat", type="messages", shards = 1, replicas = 0, refreshInterval = "-1")
public class SaveMessage {
	@Id String id;  // auto generated rows id
	private String who;
	private String text;
	private Date createDate;
	private String path;
	
	public SaveMessage create(String who, String message, String path) {
		this.who = who;
		this.text = message;
		this.createDate = new Date();
		this.path = path;
		return this;
	}
	
}