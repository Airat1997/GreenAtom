package com.example.demo;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@Component
class DataLoader {
	private final TopicRepository topicRepository;

	public DataLoader(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	@PostConstruct
	private void loadData() {
		topicRepository.saveAll(List.of(
				new Topic("Java"),
				new Topic("C++"),
				new Topic("Python"),
				new Topic("Golang")
		));
	}
}

@RestController
@RequestMapping("/topics")
class RestApiDemoController {
	private final TopicRepository topicRepository;

	public RestApiDemoController(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	@GetMapping
	Iterable<Topic> getTopics() {
		return topicRepository.findAll();
	}

	@GetMapping("/{id}")
	Optional<Topic> getTopicById(@PathVariable String id) {
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

@Entity
class Topic {
	@Id
	private String id;
	private String name;

	public Topic() {
	}

	public Topic(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Topic(String name) {
		this(UUID.randomUUID().toString(), name);
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
}