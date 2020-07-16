package com.fakebank.assignment.converter;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.fakebank.assignment.Utils.Utils;
import com.fakebank.assignment.model.AccountStatement;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFileConverter {

    public List<AccountStatement> convertToListOfAccountStatement(MultipartFile file) throws IOException, com.opencsv.exceptions.CsvValidationException {
        InputStreamReader inputStream;
        inputStream = new InputStreamReader(file.getInputStream());
        Reader reader = new BufferedReader(inputStream);
        return csvMapper(reader);

    }

    private List<AccountStatement> csvMapper(Reader reader) throws IOException, CsvValidationException {
        List<AccountStatement> response = new ArrayList<>();
        CSVReader csvReader = new CSVReader(reader);
        String[] csvLine;
        // Extract the first csvLine in order to validate it
        validateCsvHeader(csvReader.readNext());

        while ((csvLine = csvReader.readNext()) != null) {
            String transactionRef = csvLine[0];
            String accountNumber = csvLine[1];
            String description = csvLine[2];
            // ATTENTION : Utils.roundDouble(Double.parseDouble(value)
            // In Case of a value having a fomat like "8E+1"
            // Is gonna ingest these value as Hex value,
            // for example "8E+1" gets converted in the value 80.00
            double startBalance = Utils.roundDouble(Double.parseDouble(csvLine[3]));
            double mutation = Utils.roundDouble(Double.parseDouble(csvLine[4]));
            double endBalance = Utils.roundDouble(Double.parseDouble(csvLine[5]));
            response.add(new AccountStatement(transactionRef, accountNumber,
                    startBalance, mutation, description, endBalance));
        }
        reader.close();
        csvReader.close();
        return response;
    }

    private void validateCsvHeader(String[] header) throws CsvValidationException {
        String transactionRef = header[0];
        String accountNumber = header[1];
        String description = header[2];
        String startBalance = header[3];
        String mutation = header[4];
        String endBalance = header[5];
        if (!transactionRef.equals("Reference") ||
                !accountNumber.equals("Account Number") ||
                !description.equals("Description") ||
                !startBalance.equals("Start Balance") ||
                !mutation.equals("Mutation") ||
                !endBalance.equals("End Balance")) {

            throw new com.opencsv.exceptions.CsvValidationException("Header of CSV File not compatible with default format." +
                    " Header must be : " +
                    " Reference,Account Number,Description,Start Balance,Mutation,End Balance");
        }
    }


}
