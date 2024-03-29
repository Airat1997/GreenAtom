package com.example.demo;

import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/messages")
	Iterable<Message> getMessages(){
		return messageRepository.findAll();
	}

	@GetMapping("/messages/{topicId}")
	Iterable<Message> getAllMessagesTopicById(@PathVariable String topicId){
		return messageRepository.findByTopicId(topicId);
	}

	@GetMapping("/{id}")
	Optional<Topic> getAllMessageTopicById(@PathVariable String id) {
		return topicRepository.findById(id);
	}


	@PostMapping
	Topic postTopic(@RequestBody Topic topic) {
		return topicRepository.save(topic);
	}

	@PutMapping("/{id}")
	ResponseEntity<Topic> putTopic(@PathVariable String id,
									@RequestBody Topic topic) {

		return (topicRepository.existsById(id))
				? new ResponseEntity<>(topicRepository.save(topic), HttpStatus.OK)
				: new ResponseEntity<>(topicRepository.save(topic), HttpStatus.CREATED);
	}

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

	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL,orphanRemoval = true)
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
}
