package net.ion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ion.dao.MessageRepository;
import net.ion.entity.Message;

@Service
public class MessageService {
	
	@Autowired 
	private MessageRepository dao;
	
	public Message save(final String who,final String message, final String path) {
		Message msg = new Message().create(who, message, path);
		return dao.save(msg);
	}
	
	public Iterable<Message> findByPath(String path) {
		return dao.findByPath(path);
	}

}