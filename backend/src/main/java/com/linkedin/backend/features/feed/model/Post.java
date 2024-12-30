package com.linkedin.backend.features.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String content;
    private String picture;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="author_id",nullable = false)
    private AuthenticationUser author;
    @ManyToMany
    @JoinTable(
            name = "posts_likes",
            joinColumns = @JoinColumn(name="post_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<AuthenticationUser> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    public Post(String content, AuthenticationUser author) {
        this.content = content;
        this.author = author;
    }

    public Post() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public AuthenticationUser getAuthor() {
        return author;
    }

    public void setAuthor(AuthenticationUser author) {
        this.author = author;
    }

    public Set<AuthenticationUser> getLikes() {
        return likes;
    }

    public void setLikes(Set<AuthenticationUser> likes) {
        this.likes = likes;
    }
}
