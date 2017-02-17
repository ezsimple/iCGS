package net.ion.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import net.ion.entity.Message;

public interface MessageRepository extends ElasticsearchRepository<Message, String> {
	Iterable<Message> findByPath(String path);
}