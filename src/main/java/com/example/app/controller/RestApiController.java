package com.example.app.controller;

import com.example.app.dto.*;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.*;
import com.example.app.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
class RestApiController {
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    public RestApiController(TopicRepository topicRepository, MessageRepository messageRepository) {
        this.topicRepository = topicRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/topic")
    ResponseEntity<?> postTopic(@RequestBody TopicWithMessageRequest request) {
        Topic topic = new Topic(request.getTopicName());
        topic = topicRepository.save(topic);
        Message message = new Message(request.getMessage().getId(), request.getMessage().getText(), request.getMessage().getAuthor(), request.getMessage().getCreated());
        message.setTopic(topic);
        messageRepository.save(message);
        List<Message> messages = messageRepository.findByTopicId(topic.getId());
        return ResponseEntity.ok(createTopicWithMessages(topic, messages));
    }

    @PutMapping("/topic")
    public ResponseEntity<?> updateTopic(@RequestBody TopicRequest request) {
        Optional<Topic> existingTopicOptional = topicRepository.findById(request.getId());
        if (existingTopicOptional.isPresent()) {
            Topic newTopic = new Topic(request.getId(), request.getName(), request.getCreated());
            Topic updatedTopic = topicRepository.save(newTopic);
            List<Message> messages = messageRepository.findByTopicId(updatedTopic.getId());
            return ResponseEntity.ok(createTopicWithMessages(updatedTopic, messages));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/topic")
    Iterable<Topic> getTopics() {
        return topicRepository.findAll();
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<TopicWithMessages> getAllMessagesTopicById(@PathVariable String topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
        List<Message> messages = messageRepository.findByTopicId(topicId);
        return ResponseEntity.ok(createTopicWithMessages(topic, messages));
    }

    @PostMapping("/topic/{topicId}/message")
    public ResponseEntity<TopicWithMessages> postMessage(@PathVariable String topicId, @RequestBody MessageRequest request) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        Message message = new Message(request.getId(), request.getText(), request.getAuthor(), request.getCreated());
        message.setTopic(topic);
        messageRepository.save(message);
        List<Message> messages = messageRepository.findByTopicId(topicId);
        return ResponseEntity.ok(createTopicWithMessages(topic, messages));
    }

    @PutMapping("/topic/{topicId}/message")
    public ResponseEntity<TopicWithMessages> putMessage(@PathVariable String topicId, @RequestBody MessageRequest request) {
        return postMessage(topicId, request);
    }

    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable String messageId) {
        messageRepository.deleteById(messageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successful operation");
    }

    private TopicWithMessages createTopicWithMessages(Topic topic, List<Message> messages) {
        TopicWithMessages topicWithMessages = new TopicWithMessages();
        topicWithMessages.setCreated(topic.getCreated());
        topicWithMessages.setId(topic.getId());
        topicWithMessages.setName(topic.getName());
        topicWithMessages.setMessages(messages);
        return topicWithMessages;
    }

}
