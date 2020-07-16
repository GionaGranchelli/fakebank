package com.fakebank.assignment.unit;

import com.opencsv.exceptions.CsvValidationException;
import com.fakebank.assignment.config.FileTypeConfig;
import com.fakebank.assignment.converter.CsvFileConverter;
import com.fakebank.assignment.converter.XmlFileConverter;
import com.fakebank.assignment.model.AccountStatement;
import com.fakebank.assignment.model.StatementProcessorResponse;
import com.fakebank.assignment.service.StatementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.fakebank.assignment.config.UtilsTest.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class StatementServiceTest {

    private HashMap<String, String> compatibleFileType = new HashMap<>();

    private StatementService statementService;
    private FileTypeConfig fileTypeConfig;

    @Before
    public void init() {
        // Init mocked property with available mimetypes
        compatibleFileType.put("csv", CSV_MIMETYPE);
        compatibleFileType.put("xml", XML_MIMETYPE);
        compatibleFileType.put("axml", APPLICATION_XML_MIMETYPE);
        // Instantiate Service
        fileTypeConfig = new FileTypeConfig();
        fileTypeConfig.setFiletype(compatibleFileType);
        XmlFileConverter xmlFileConverter = new XmlFileConverter();
        xmlFileConverter.setXmlSchema(SAMPLE_XSD_PATH);
        CsvFileConverter csvFileConverter = new CsvFileConverter();

        statementService = new StatementService(fileTypeConfig, xmlFileConverter, csvFileConverter);
    }

    @Test
    public void canLoadInternalResource() {
        // Test if test files are loader correctly
        File fileCsv = new File(SAMPLE_CSV_PATH);
        assertTrue(fileCsv.exists());

        File fileXml = new File(SAMPLE_XML_PATH);
        assertTrue(fileXml.exists());

        File fileXSD = new File(SAMPLE_XSD_PATH);
        assertTrue(fileXSD.exists());
    }

    @Test
    public void csvValidationWorksAsExpected() {
        File file = new File(SAMPLE_CSV_PATH);
        FileInputStream inputFile;
        try {
            // Mock Property
            inputFile = new FileInputStream(file.getAbsolutePath());
            MockMultipartFile multipartFile = new MockMultipartFile(SAMPLE_CSV, SAMPLE_CSV, CSV_MIMETYPE, inputFile);

            List<AccountStatement> accountStatements = statementService.validateAndConvertToRecords(multipartFile);
            assertEquals(10, accountStatements.size());

            List<StatementProcessorResponse> statementProcessorResponses = statementService.extractNotValidTransaction(accountStatements);
            assertEquals(2, statementProcessorResponses.size());
        } catch (IOException | SAXException | CsvValidationException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void XMLValidationWorksAsExpected() {
        File file = new File(SAMPLE_XML_PATH);
        FileInputStream inputFile;
        try {
            // Mock MultipartFile
            inputFile = new FileInputStream(file.getAbsolutePath());
            MockMultipartFile multipartFile = new MockMultipartFile(SAMPLE_XML, SAMPLE_XML, XML_MIMETYPE, inputFile);

            List<AccountStatement> accountStatements = statementService.validateAndConvertToRecords(multipartFile);
            assertEquals(10, accountStatements.size());

            List<StatementProcessorResponse> statementProcessorResponses = statementService.extractNotValidTransaction(accountStatements);
            assertEquals(2, statementProcessorResponses.size());
        } catch (IOException | SAXException | CsvValidationException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}
