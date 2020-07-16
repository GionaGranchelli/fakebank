package com.fakebank.assignment.converter;

import com.fakebank.assignment.Utils.Utils;
import com.fakebank.assignment.exception.NotSupportedXmlSchema;
import com.fakebank.assignment.model.AccountStatement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static javax.xml.parsers.SAXParserFactory.newInstance;

@Component
public class XmlFileConverter {

    @Value("${xml-schema}")
    private String xmlSchema;

    public List<AccountStatement> convertToListOfAccountStatement(File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = newInstance();
        SAXParser saxParser;

        saxParser = factory.newSAXParser();
        AccountStatementParser accountStatementParser = new AccountStatementParser();
        saxParser.parse(file.getAbsolutePath(), accountStatementParser);
        file.delete();
        return accountStatementParser.getAccountStatementList()
                .getStatements();


    }

    private void validateXml(Schema schema, String xmlName) throws IOException, SAXException {

        Validator validator = schema.newValidator();
        SAXSource source = new SAXSource(new InputSource(new FileInputStream(xmlName)));
        validator.validate(source);

    }

    @Cacheable
    public Schema getSchema() throws SAXException {
        Schema schema;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        schema = factory.newSchema(new File(this.xmlSchema));
        return schema;
    }


    public File validation(MultipartFile file) throws NotSupportedXmlSchema, IOException, SAXException {
        Schema recordSchema = getSchema();
        File xmlFile = Utils.convertMultipartFileToFile(file, file.getOriginalFilename());
        validateXml(recordSchema, xmlFile.getAbsolutePath());
        return xmlFile;
    }

    public String getXmlSchema() {
        return xmlSchema;
    }

    public void setXmlSchema(String xmlSchema) {
        this.xmlSchema = xmlSchema;
    }
}
