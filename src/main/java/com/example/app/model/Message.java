package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Message {
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

    public Message(String id, String text, String author, String created) {
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
