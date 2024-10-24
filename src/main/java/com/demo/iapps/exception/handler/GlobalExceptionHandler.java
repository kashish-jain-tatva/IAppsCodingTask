package com.demo.iapps.exception.handler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.xml.sax.SAXParseException;

import com.demo.iapps.exception.InvalidFilterTypeException;
import com.demo.iapps.exception.InvalidInputFileException;
import com.demo.iapps.exception.NoRecordFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { SAXParseException.class, InvalidFilterTypeException.class, IOException.class,
			ParserConfigurationException.class, InvalidInputFileException.class })
	public ResponseEntity<Object> handleBadRequestExceptions(Exception ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(value = NoRecordFoundException.class)
	public ResponseEntity<Object> handleNoRecordFoundException(NoRecordFoundException ex) {
		return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
	}

	@ExceptionHandler(value = NumberFormatException.class)
	public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				"Error occured while parsing: " + ex.getMessage() + " please provide a proper number in path variable");
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}

}
