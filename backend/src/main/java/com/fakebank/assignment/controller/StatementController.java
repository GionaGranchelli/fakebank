package com.fakebank.assignment.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.fakebank.assignment.exception.*;
import com.fakebank.assignment.model.AccountStatement;
import com.fakebank.assignment.model.StatementProcessorResponse;
import com.fakebank.assignment.service.StatementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin
class StatementController {

    Logger logger = LoggerFactory.getLogger(StatementController.class);

    private StatementService statementService;

    @Autowired
    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @PostMapping("/loadStatement")
    private ResponseEntity<List<StatementProcessorResponse>> loadStatementRecordCsv(@RequestParam("file") MultipartFile file) {
        logger.info("Request Received");
        List<AccountStatement> listOfAccountRecord;
        if (file.isEmpty()) {
            logger.error("Empty File");
            throw new EmptyFileException();
        }
        if (!statementService.isFileTypeValid(file.getContentType())) {
            logger.error("NotSupportedMediaTypeException");
            throw new NotSupportedMediaTypeException(file.getContentType());
        }
        try {
            listOfAccountRecord = statementService.validateAndConvertToRecords(file);
            logger.info("listOfAccountRecord size:{}", listOfAccountRecord.size());
        } catch (IOException e) {
            logger.error("IOException");
            throw new FileErrorException(e.getMessage());
        } catch (SAXException e) {
            logger.error("SAXException");
            throw new NotSupportedXmlSchema(e.getMessage());
        } catch (CsvValidationException e) {
            logger.error("CsvValidationException");
            throw new NotSupportedCsvException(e.getMessage());
        } catch (ParserConfigurationException e) {
            logger.error("ParserConfigurationException");
            throw new XmlParsingException(e.getMessage());
        }
        logger.info("Request Successful");
        return ResponseEntity
                .status(OK)
                .body(statementService.extractNotValidTransaction(listOfAccountRecord));
    }
}
