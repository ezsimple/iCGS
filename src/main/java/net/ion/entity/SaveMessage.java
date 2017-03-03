package net.ion.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName="chat", type="messages")
public class SaveMessage {

	@Id 
	String id; 
	private String who;
	private String text;
	private Date createDate;
	private String path;
	
	public SaveMessage (String id, String who, String message, String path) {
		this.id = id;
		this.who = who;
		this.text = message;
		this.createDate = new Date();
		this.path = path;
	}
	
}