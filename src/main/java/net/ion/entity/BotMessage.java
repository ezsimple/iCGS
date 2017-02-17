package net.ion.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BotMessage {

    private String req;
    private String res;
    private Date reqTime;
    private Date resTime;

    public BotMessage(String req, String res) {
        this.req = req;
        this.res = res;
    }

    public BotMessage setRequest(String req) {
    	this.req = req;
    	return this;
    }

    public BotMessage setResponse(String res) {
    	this.res = res;
    	return this;
    }
    
    public BotMessage setReqtime(Date reqTime) {
    	this.reqTime = reqTime;
    	return this;
    }
    
    public BotMessage setRestime(Date resTime) {
    	this.resTime = resTime;
    	return this;
    }

}