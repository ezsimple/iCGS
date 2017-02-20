package net.ion.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import net.ion.entity.SaveMessage;

public interface MessageRepository extends ElasticsearchRepository<SaveMessage, String> {
	Page<SaveMessage> findByPath(String path, Pageable pageable);
}