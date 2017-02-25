package net.ion.event;

import java.util.Date;

import lombok.Data;

@Data
public class ConnectEvent {
	private String username;
	private Date time;
	
	private ConnectEvent() {}
	public ConnectEvent(String username) {
		this.username = username;
		this.time = new Date();
	}

}
