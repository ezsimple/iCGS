package net.ion.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import net.ion.entity.SaveMessage;

public interface MessageRepository extends ElasticsearchRepository<SaveMessage, String> {
	Page<SaveMessage> findByPath(String path, Pageable pageable);
	Iterable<SaveMessage> findByPath(String path, Sort sort);
	SaveMessage findById(String id);
	Iterable<SaveMessage> findByCreateDateLessThanEqualAndPath(Long createDate, String path, Sort sort);
	Iterable<SaveMessage> findByCreateDateGreaterThanAndPath(Long createDate, String path, Sort sort);
}