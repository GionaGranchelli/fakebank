package com.fakebank.assignment.converter;

import com.fakebank.assignment.model.AccountStatement;
import com.fakebank.assignment.model.AccountStatementList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class AccountStatementParser extends DefaultHandler {

    private static final String RECORDS = "records";
    private static final String RECORD = "record";
    private static final String ACCOUNT_NUMBER = "accountNumber";
    private static final String DESCRIPTION = "description";
    private static final String START_BALANCE = "startBalance";
    private static final String MUTATION = "mutation";
    private static final String END_BALANCE = "endBalance";

    private AccountStatementList accountStatementList;
    private String element;

    private AccountStatement latestArticle() {
        List<AccountStatement> articleList = accountStatementList.getStatements();
        int latestArticleIndex = articleList.size() - 1;
        return articleList.get(latestArticleIndex);
    }

    public AccountStatementList getAccountStatementList() {
        return accountStatementList;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        if (accountStatementList == null){
            accountStatementList = new AccountStatementList();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case RECORDS:
                accountStatementList = new AccountStatementList();
                break;
            case RECORD:
                String transactionRef = attributes.getValue(0);
                AccountStatement accountStatement = new AccountStatement();
                accountStatement.setTransactionRef(transactionRef);
                accountStatementList.getStatements().add(accountStatement);
                break;
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        switch (qName) {
            case ACCOUNT_NUMBER:
                latestArticle().setAccountNumber(element);
                break;
            case DESCRIPTION:
                latestArticle().setDescription(element);
                break;
            case START_BALANCE:
                latestArticle().setStartBalance(Double.parseDouble(element));
                break;
            case MUTATION:
                latestArticle().setMutation(Double.parseDouble(element));
                break;
            case END_BALANCE:
                latestArticle().setEndBalance(Double.parseDouble(element));
                break;
            default:
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        element = new String(ch, start, length);
    }

}
