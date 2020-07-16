package com.fakebank.assignment.exception;

/**
 * This class is used as response Pojo in case of error
 * */
public class ErrorMessage {

    int code;
    String errorDescription;

    public ErrorMessage() {
    }

    public ErrorMessage(int code, String errorDescription) {
        this.code = code;
        this.errorDescription = errorDescription;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
