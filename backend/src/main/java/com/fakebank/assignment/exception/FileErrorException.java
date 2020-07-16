package com.fakebank.assignment.exception;

public class FileErrorException extends RuntimeException {

    public FileErrorException(String message) {
        super("Error Reading File, caused by: " + message);
    }
}
