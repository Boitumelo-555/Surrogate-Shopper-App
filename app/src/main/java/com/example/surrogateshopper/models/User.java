package com.example.surrogateshopper.models;

public class User {
    private final int id;
    private final String name;
    private final String email;
    private final String role; // "requester" or "volunteer"

    public User(int id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
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

    public String getRole() {
        return role;
    }
}
