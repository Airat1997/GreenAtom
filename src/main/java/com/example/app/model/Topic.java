package com.example.app.model;

import com.example.app.model.Message;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Topic {
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
