package net.ion.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import net.ion.entity.SaveMessage;

public interface MessageRepository extends ElasticsearchRepository<SaveMessage, String> {
	Iterable<SaveMessage> findByPath(String path);
}