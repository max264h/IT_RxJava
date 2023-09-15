package com.example.rxjavademo;

public class DataResponse {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    @Override
    public String toString() {
        return "DataResponse{" +
                "\npostId=" + postId +
                ",\n id=" + id +
                ",\n name='" + name + '\'' +
                ",\n email='" + email + '\'' +
                ",\n body='" + body + '\'' +
                '}';
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
