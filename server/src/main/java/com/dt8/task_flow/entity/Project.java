package com.dt8.task_flow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn(name="owner_id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "users_projects",
            joinColumns = @JoinColumn(name="project_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    List<User> users;

    public Project() {
    }

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = ProjectStatus.NONE;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }
}
