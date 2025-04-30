package com.example.surrogateshopper.models;

public class ThankYouMessage {
    private final int id;
    private final int requesterId;
    private final int volunteerId;
    private final String message;

    public ThankYouMessage(int id, int requesterId, int volunteerId, String message) {
        this.id = id;
        this.requesterId = requesterId;
        this.volunteerId = volunteerId;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public String getMessage() {
        return message;
    }
}
