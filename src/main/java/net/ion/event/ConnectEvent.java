package net.ion.event;

import java.util.Date;

import lombok.Data;

@Data
public class ConnectEvent {
	private String operator;
	private String username;
	private String sessionId;
	private Date time;
	
	private ConnectEvent() {}
	public ConnectEvent(String operator, String username, String sessionId) {
		this.operator = operator;
		this.username = username;
		this.sessionId = sessionId;
		this.time = new Date();
	}
	
	public String getUsername() {
		return this.username+"@"+this.sessionId;
	}

}
