package com.example.surrogateshopper.models;

public class ShoppingRequest {
    private String title;
    private String description;

    public ShoppingRequest() {
    }

    public ShoppingRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
