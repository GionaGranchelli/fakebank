package com.fakebank.assignment.exception;

public class NotSupportedXmlSchema extends RuntimeException {

    public NotSupportedXmlSchema(String message) {
        super("Xml File not valid, caused by: " + message);
    }
}
