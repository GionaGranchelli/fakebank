package com.fakebank.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatementProcessorResponse {

    @JsonProperty("transactionId")
    private String transactionRef;
    @JsonProperty("transactionDescription")
    private String description;
    @JsonProperty("failureDescription")
    private ValidationResponse validationResponse;

    public StatementProcessorResponse() { }

    public StatementProcessorResponse(String transactionRef, String description, ValidationResponse validationResponse) {
        this.transactionRef = transactionRef;
        this.description = description;
        this.validationResponse = validationResponse;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ValidationResponse getValidationResponse() {
        return validationResponse;
    }

    public void setValidationResponse(ValidationResponse validationResponse) { this.validationResponse = validationResponse; }
}
