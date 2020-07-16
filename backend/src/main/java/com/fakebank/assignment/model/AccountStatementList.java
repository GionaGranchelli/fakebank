package com.fakebank.assignment.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Container of AccountStatement in the XML files
 * */
public class AccountStatementList {

    private List<AccountStatement> statements;

    public AccountStatementList() { this.statements = new ArrayList<>(); }

    public AccountStatementList(List<AccountStatement> statements) {
        this.statements = statements;
    }

    public List<AccountStatement> getStatements() {
        return statements;
    }

    public void setStatements(List<AccountStatement> statements) {
        this.statements = statements;
    }
}
