package net.ion.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import net.ion.entity.SaveMessage;

public interface MessageRepository extends ElasticsearchRepository<SaveMessage, String> {
	Page<SaveMessage> findByPath(String path, Pageable pageable);
	Iterable<SaveMessage> findByPath(String path);
	SaveMessage findById(String Id);
	Iterable<SaveMessage> findBycreateDateLessThanAndPath(Date createDate, String path);
	Iterable<SaveMessage> findBycreateDateGreaterThanAndPath(Date createDate, String path);
}