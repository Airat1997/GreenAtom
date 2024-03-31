package com.example.app.dto;

public class MessageRequest {
    private String id;
    private String text;
    private String author;
    private String created;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreated() {
        return created;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
