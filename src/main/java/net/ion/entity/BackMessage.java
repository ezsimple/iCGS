package net.ion.entity;

import java.util.Date;
import lombok.Data;

@Data
public class BackMessage {

	private String id;
    private String who;
    private String text;
    private Date createDate;

    private BackMessage() {}
    public BackMessage(String id, String who, String text, Date date) {
    	this.id = id;
        this.who = who;
        this.text = text;
        this.createDate = date;
    }

}