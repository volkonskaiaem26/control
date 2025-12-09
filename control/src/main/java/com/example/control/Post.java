package com.example.control;

import java.time.LocalDateTime;

public class Post {

    long id;
    String title;
    int price;
    String author;
    String message;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Post() {
    }

    public Post(String title, long id, int price, String message) {
        this.title = title;
        this.id = id;
        this.price = price;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
