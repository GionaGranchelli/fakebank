package com.fakebank.assignment.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fakebank.assignment.exception.*;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
class StatementControllerAdvice {

    @ExceptionHandler(value = EmptyFileException.class)
    public ResponseEntity<ErrorMessage> handleEmptyFileException(EmptyFileException ex) {
        return ResponseEntity.badRequest().body(new ErrorMessage(BAD_REQUEST.value(),ex.getMessage()));
    }

    @ExceptionHandler(value = NotSupportedMediaTypeException.class)
    public ResponseEntity<ErrorMessage> handleNotSupportedMediaTypeException(NotSupportedMediaTypeException ex) {
        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(new ErrorMessage(UNSUPPORTED_MEDIA_TYPE.value(),ex.getMessage()));
    }

    @ExceptionHandler(value = NotSupportedXmlSchema.class)
    public ResponseEntity<ErrorMessage> handleNotSupportedXmlSchemaException(NotSupportedXmlSchema ex) {
        return ResponseEntity.badRequest().body(new ErrorMessage(BAD_REQUEST.value(),ex.getMessage()));
    }

    @ExceptionHandler(value = FileErrorException.class)
    public ResponseEntity<ErrorMessage> handleFileErrorException(FileErrorException ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorMessage(INTERNAL_SERVER_ERROR.value(),ex.getMessage()));
    }

    @ExceptionHandler(value = NotSupportedCsvException.class)
    public ResponseEntity<ErrorMessage> handleNotSupportedCsvHeader(NotSupportedCsvException ex) {
        return ResponseEntity.badRequest().body(new ErrorMessage(BAD_REQUEST.value(),ex.getMessage()));
    }
}
