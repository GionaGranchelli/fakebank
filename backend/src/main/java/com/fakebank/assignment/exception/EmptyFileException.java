package com.fakebank.assignment.exception;

public class EmptyFileException extends RuntimeException {

    public EmptyFileException() {
        super("File is Empty");
    }
}
