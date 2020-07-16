package com.fakebank.assignment.sax;

import com.fakebank.assignment.converter.AccountStatementParser;
import com.fakebank.assignment.model.AccountStatement;
import com.fakebank.assignment.model.AccountStatementList;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// HERE JUST TEST THE SAX parse
public class AccountStatementParserMainUnitTest {

    private static final String SAMPLE_XML_PATH = "src/test/java/static/records.xml";

    @Test
    public void givenAnXmlFileParseIsSuccessful() throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        AccountStatementParser accountStatementParser = new AccountStatementParser();
        saxParser.parse(SAMPLE_XML_PATH, accountStatementParser);

        AccountStatementList response = accountStatementParser.getAccountStatementList();

        assertNotNull(response);
        List<AccountStatement> articles = response.getStatements();

        assertNotNull(articles);
        assertEquals(10, articles.size());
    }
}
