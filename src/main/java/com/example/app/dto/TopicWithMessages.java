package com.example.app.dto;

import com.example.app.model.Message;

import java.util.List;

public class TopicWithMessages {
    private String id;
    private String name;

    private String created;
    private List<Message> messages;

    public TopicWithMessages() {
    }

    ;

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
