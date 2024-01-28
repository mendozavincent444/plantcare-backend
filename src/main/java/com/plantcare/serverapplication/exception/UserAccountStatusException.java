package com.plantcare.serverapplication.exception;

public class UserAccountStatusException extends RuntimeException {

    private String message;

    public UserAccountStatusException(String message) {
        super(message);
        this.message = message;
    }
}
