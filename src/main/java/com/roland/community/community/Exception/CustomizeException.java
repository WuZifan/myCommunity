package com.roland.community.community.Exception;

public class CustomizeException extends RuntimeException {

    private String message;
    public CustomizeException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
