package com.fakebank.assignment.exception;

public class NotSupportedMediaTypeException extends RuntimeException {

    public NotSupportedMediaTypeException(String message) {
        super("File media type: " + message + ", is not supported");
    }
}
