package com.fakebank.assignment.service;

import com.opencsv.exceptions.CsvValidationException;
import com.fakebank.assignment.Utils.Utils;
import com.fakebank.assignment.config.FileTypeConfig;
import com.fakebank.assignment.converter.CsvFileConverter;
import com.fakebank.assignment.converter.XmlFileConverter;
import com.fakebank.assignment.model.AccountStatement;
import com.fakebank.assignment.model.StatementProcessorResponse;
import com.fakebank.assignment.model.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StatementService {

    private static final String NO_DESCRIPTION = "NO DESCRIPTION";
    private static final String CSV = "csv";
    private FileTypeConfig fileTypeConfig;
    private XmlFileConverter xmlFileConverter;
    private CsvFileConverter csvFileConverter;

    @Autowired
    public StatementService(FileTypeConfig fileTypeConfig, XmlFileConverter xmlFileConverter, CsvFileConverter csvFileConverter) {
        this.fileTypeConfig = fileTypeConfig;
        this.xmlFileConverter = xmlFileConverter;
        this.csvFileConverter = csvFileConverter;
    }

    public List<AccountStatement> validateAndConvertToRecords(MultipartFile file) throws IOException, SAXException, CsvValidationException, ParserConfigurationException {
        if (isCsvFile(file)) {
            return csvFileConverter.convertToListOfAccountStatement(file);
        } else {
            File temporaryXmlFile = xmlFileConverter.validation(file);
            return xmlFileConverter.convertToListOfAccountStatement(temporaryXmlFile);
        }
    }

    public List<StatementProcessorResponse> extractNotValidTransaction(List<AccountStatement> listOfAccountRecord) {
        HashMap<String, StatementProcessorResponse> allStatements = new HashMap<>();
        return listOfAccountRecord.stream()
                .map(accountStatement -> {
                    String transactionRef = accountStatement.getTransactionRef();
                    String transactionDescription = accountStatement.getDescription() != null ? accountStatement.getDescription() : NO_DESCRIPTION;
                    double startBalance = accountStatement.getStartBalance();
                    double mutation = accountStatement.getMutation();
                    double expectedBalance = accountStatement.getEndBalance();

                    if (allStatements.containsKey(transactionRef)) {
                        StatementProcessorResponse notUniqueStatement = new StatementProcessorResponse(transactionRef, transactionDescription, ValidationResponse.TRANSACTION_NOT_UNIQUE);
                        allStatements.put(transactionRef + "-err-" + UUID.randomUUID().toString(), notUniqueStatement);
                        return notUniqueStatement;
                    }

                    double actualEndBalance = Utils.roundDouble((startBalance + mutation));
                    if (expectedBalance != actualEndBalance) {
                        return new StatementProcessorResponse(transactionRef, transactionDescription, ValidationResponse.BALANCE_MISMATCH);
                    }

                    StatementProcessorResponse validStatement = new StatementProcessorResponse(transactionRef, transactionDescription, ValidationResponse.VALID);
                    allStatements.put(transactionRef, validStatement);
                    return validStatement;
                })
                .filter(statement -> !statement.getValidationResponse().equals(ValidationResponse.VALID))
                .collect(Collectors.toList());
    }

    private boolean isCsvFile(MultipartFile file) {
        return getFileType(Objects.requireNonNull(file.getContentType())).equals(fileTypeConfig.getFiletype().get(CSV));
    }

    public boolean isFileTypeValid(String contentType) {
        return fileTypeConfig.getFiletype().containsValue(contentType);
    }

    private String getFileType(String contentType) {
        return fileTypeConfig.getFiletype()
                .values().stream()
                .filter(contentType::equals)
                .findFirst()
                .orElse(fileTypeConfig.getFiletype().get(CSV));
    }
}
