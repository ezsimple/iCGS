package net.ion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ion.dao.MessageRepository;
import net.ion.entity.SaveMessage;

@Service
public class SaveMessageService {
	
	@Autowired 
	private MessageRepository dao;
	
	public SaveMessage save(final String who,final String message, final String path) {
		SaveMessage msg = new SaveMessage().create(who, message, path);
		return dao.save(msg);
	}
	
	public Page<SaveMessage> findByPath(String path, Pageable pageable) {
		return dao.findByPath(path, pageable);
	}

}