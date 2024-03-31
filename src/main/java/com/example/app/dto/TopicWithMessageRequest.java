package com.example.app.dto;

import com.example.app.model.*;


public class TopicWithMessageRequest {

    private String id;

    private String created;
    private String topicName;
    private Message message;

    public TopicWithMessageRequest() {
    }

    public TopicWithMessageRequest(String id, String created, String topicName, Message message) {
        this.topicName = topicName;
        this.message = message;
        this.id = id;
        this.created = created;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
