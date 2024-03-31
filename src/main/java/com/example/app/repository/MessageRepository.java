package com.example.app.repository;

import com.example.app.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, String> {
    List<Message> findByTopicId(String topicId);
}
