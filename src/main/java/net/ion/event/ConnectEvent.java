package net.ion.event;

import java.util.Date;

import lombok.Data;
import net.ion.service.WisenutService;

@Data
public class ConnectEvent {
	
	private String operator;
	private String username;
	private String simpSessionId;
	private Date time;
	
	
	// wnSessionId를 여기서 처리하는 게 맞는건지? 
	// exception 발생하면 서비스 중단일 텐데....
	// 그리고 wisenut SessionId가 expire 된다면 ???
	// 여기서 wnSessionId를 받아오는 건 아닌것 같다.
	// 2017.03.16
	
	private ConnectEvent() {}
	public ConnectEvent(String operator, String username, String simpSessionId) {
		this.operator = operator;
		this.username = username;
		this.simpSessionId = simpSessionId;
		this.time = new Date();
	}
	
	public String getUniquename() {
		return this.username+"@"+this.simpSessionId;
	}

}
