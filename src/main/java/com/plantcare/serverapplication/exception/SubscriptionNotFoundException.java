package com.plantcare.serverapplication.exception;

public class SubscriptionNotFoundException extends RuntimeException {

    private String message;

    public SubscriptionNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
