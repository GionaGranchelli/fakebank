package com.fakebank.assignment.model;
/**
 *
 * Field, Description
 * - Transaction reference, A numeric value
 * - Account number, An IBAN
 * - Start Balance, The starting balance in Euros
 * - Mutation, Either an addition (+) or a deduction (-)
 * - Description, Free text
 * - End Balance, The end balance in Euros
 */
public class AccountStatement {

    private  String transactionRef;
    private  String accountNumber;
    private  Double startBalance;
    private  Double mutation;
    private  String description;
    private  Double endBalance;

    public AccountStatement(String transactionRef, String accountNumber, Double startBalance, Double mutation, String description, Double endBalance) {
        this.transactionRef = transactionRef;
        this.accountNumber = accountNumber;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.description = description;
        this.endBalance = endBalance;
    }

    public AccountStatement() { }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(Double startBalance) {
        this.startBalance = startBalance;
    }

    public Double getMutation() {
        return mutation;
    }

    public void setMutation(Double mutation) {
        this.mutation = mutation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(Double endBalance) {
        this.endBalance = endBalance;
    }
}
