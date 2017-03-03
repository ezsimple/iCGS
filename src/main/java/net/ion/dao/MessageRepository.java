package net.ion.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import net.ion.entity.SaveMessage;

public interface MessageRepository extends ElasticsearchRepository<SaveMessage, String> {
	Page<SaveMessage> findByPath(String path, Pageable pageable);
	Iterable<SaveMessage> findByPath(String path);
	SaveMessage findById(String id);
	Iterable<SaveMessage> findByIdLessThanEqualAndPath(String id, String path);
	Iterable<SaveMessage> findByIdGreaterThanAndPath(String id, String path);
}