package com.fakebank.assignment.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is used for:
 * - Store Constants used in other test
 * - Provide Mock Multipart File
 */

public class UtilsTest {
    public static final String CSV_MIMETYPE = "text/csv";
    public static final String XML_MIMETYPE = "text/xml";
    private static final String TEXT_MIMETYPE = "text/plain";
    public static final String APPLICATION_XML_MIMETYPE = "application/xml";


    public static final String SAMPLE_CSV_PATH = "src/test/java/static/records.csv";
    public static final String SAMPLE_CSV = "records.csv";

    public static final String SAMPLE_XSD_PATH = "src/main/resources/xml/schema-records.xsd";

    public static final String SAMPLE_XML_PATH = "src/test/java/static/records.xml";
    public static final String SAMPLE_XML = "records.xml";

    private static final String EMPTY_FILE_PATH = "src/test/java/static/emptyfile";
    private static final String SAMPLE_FILE = "emptyfile";

    private static final String WRONG_XML_PATH = "src/test/java/static/wrong.xml";
    private static final String WRONG_XML = "wrong.xml";

    private static final String WRONG_CSV_PATH = "src/test/java/static/wrong.csv";
    private static final String WRONG_CSV = "wrong.csv";


    @NotNull
    public static MockMultipartFile getCSVMockMultipartFile() throws IOException {
        File csvFile = new File(SAMPLE_CSV_PATH);
        FileInputStream inputFile = new FileInputStream(csvFile.getAbsolutePath());
        return new MockMultipartFile("file", SAMPLE_CSV, CSV_MIMETYPE, inputFile);
    }

    @NotNull
    public static MockMultipartFile getXMLMockMultipartFile() throws IOException {
        File csvFile = new File(SAMPLE_XML_PATH);
        FileInputStream inputFile = new FileInputStream(csvFile.getAbsolutePath());
        return new MockMultipartFile("file", SAMPLE_XML, XML_MIMETYPE, inputFile);
    }

    @NotNull
    public static MockMultipartFile getWrongSchemaMultipartFile() throws IOException {
        File csvFile = new File(WRONG_XML_PATH);
        FileInputStream inputFile = new FileInputStream(csvFile.getAbsolutePath());
        return new MockMultipartFile("file", WRONG_XML, XML_MIMETYPE, inputFile);
    }

    @NotNull
    public static MockMultipartFile getWrongMimetypeMultipartFile() throws IOException {
        File csvFile = new File(SAMPLE_XML_PATH);
        FileInputStream inputFile = new FileInputStream(csvFile.getAbsolutePath());
        return new MockMultipartFile("file", SAMPLE_XML, TEXT_MIMETYPE, inputFile);
    }

    @NotNull
    public static MockMultipartFile getEmptyMultipartFile() throws IOException {
        File csvFile = new File(EMPTY_FILE_PATH);
        FileInputStream inputFile = new FileInputStream(csvFile.getAbsolutePath());
        return new MockMultipartFile("file", SAMPLE_FILE, XML_MIMETYPE, inputFile);
    }


    @NotNull
    public static MockMultipartFile getWrongCSVMultipartFile() throws IOException {
        File csvFile = new File(WRONG_CSV_PATH);
        FileInputStream inputFile = new FileInputStream(csvFile.getAbsolutePath());
        return new MockMultipartFile("file", WRONG_CSV, CSV_MIMETYPE, inputFile);
    }

}
