package com.linkedin.backend.features.feed.dto;

public class PostDto {
    private String content;
    private String picture;

    public PostDto() {

    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
