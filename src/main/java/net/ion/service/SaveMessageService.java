package net.ion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ion.dao.MessageRepository;
import net.ion.entity.SaveMessage;
import net.ion.utils.ObjectId;

@Service
public class SaveMessageService {
	
	@Autowired 
	private MessageRepository dao;
	
	@Autowired 
	private UUIDService svc;
	
	public SaveMessage save(final String who,final String message, final String path) {
		final String id = svc.getNextId();
		SaveMessage msg = new SaveMessage(id, who, message, path);
		// ==================================================================
		// 어떻게 _id 와 id의 값을 같게 할 수 있을까? 이 방법밖에는 없는건가?
		// ==================================================================
		// 1. 두번의 저장이 일어나므로 비추
		// String id = dao.save(msg).getId();
		// msg.setId(id);
		// 2. seqId를 생성해서 저장하도록 한다.
		// ============================================
		return dao.save(msg);
	}
	
	public Page<SaveMessage> findByPath(String path, Pageable pageable) {
		return dao.findByPath(path, pageable);
	}

}