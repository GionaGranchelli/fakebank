package com.fakebank.assignment.exception;


public class XmlParsingException extends RuntimeException {

    public XmlParsingException(String message) {
        super("XML parsing error caused by:" + message);
    }
}
