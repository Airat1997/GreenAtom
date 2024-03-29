package com.example.demo;

import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@RestController
@RequestMapping("/topic")
class RestApiDemoController {
	private final TopicRepository topicRepository;
	private final MessageRepository messageRepository;

	public RestApiDemoController(TopicRepository topicRepository, MessageRepository mesegeRepository) {
		this.topicRepository = topicRepository;
		this.messageRepository = mesegeRepository;
	}

	@GetMapping
	Iterable<Topic> getTopics() {
		return topicRepository.findAll();
	}
	@GetMapping("/{topicId}")
	public ResponseEntity<TopicWithMessages> getAllMessagesTopicById(@PathVariable String topicId) {
		Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
		List<Message> messages = messageRepository.findByTopicId(topicId);
		TopicWithMessages topicWithMessages = new TopicWithMessages();
		topicWithMessages.setId(topic.getId());
		topicWithMessages.setName(topic.getName());
		topicWithMessages.setMessages(messages);
		return ResponseEntity.ok(topicWithMessages);
	}
	@PostMapping
	Topic postTopic(@RequestBody TopicWithMessageRequest request) {
		Topic topic = new Topic(request.getTopicName());
		topic = topicRepository.save(topic);
		Message message = new Message(request.getMessage().getId(), request.getMessage().getText(), request.getMessage().getAuthor(), request.getMessage().getCreated());
		message.setTopic(topic);
		messageRepository.save(message);
		return topic;
	}



//	@GetMapping("/{id}")
//	Optional<Topic> getAllMessageTopicById(@PathVariable String id) {
//		return topicRepository.findById(id);
//	}




//	@PutMapping("/{id}")
//	ResponseEntity<Topic> putTopic(@PathVariable String id,
//									@RequestBody Topic topic) {
//
//		return (topicRepository.existsById(id))
//				? new ResponseEntity<>(topicRepository.save(topic), HttpStatus.OK)
//				: new ResponseEntity<>(topicRepository.save(topic), HttpStatus.CREATED);
//	}

	@DeleteMapping("/{id}")
	void deleteTopic(@PathVariable String id) {
		topicRepository.deleteById(id);
	}



}

interface TopicRepository extends CrudRepository<Topic, String> {}
interface MessageRepository extends CrudRepository<Message, String> {
	List<Message> findByTopicId(String topicId);
}

@Entity
class Topic {
	@Id
	private String id;
	@Column(name = "name")
	private String name;

	@Column(name = "created")
	private String created;

	public Topic() {
	}

	@OneToMany(mappedBy = "topic")
	private List<Message> messages;
	public Topic(String id, String name, String created) {
		this.id = id;
		this.name = name;
		this.created = created;
	}

	public Topic(String name) {
		this(UUID.randomUUID().toString(), name, LocalDateTime.now().toString());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreated() {
		return created;
	}
}

@Entity
class Message {
	@Id
	private String id;
	@Column
	private String text;
	@Column
	private String author;
	@Column
	private String created;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	public Message(String id, String text, String author, String created){
		this.id = id;
		this.text = text;
		this.author = author;
		this.created = created;
	}
	public Message() {
	}
	public String getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreated() {
		return created;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}

class TopicWithMessages {
	private String id;
	private String name;
	private List<Message> messages;

	public TopicWithMessages(){
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}

class TopicWithMessageRequest {
	private String topicName;
	private Message message;
	public TopicWithMessageRequest() {
	}
	public TopicWithMessageRequest(String topicName, Message message) {
		this.topicName = topicName;
		this.message = message;
	}
	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
