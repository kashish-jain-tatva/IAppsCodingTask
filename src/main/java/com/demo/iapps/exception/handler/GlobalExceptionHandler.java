package com.demo.iapps.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.xml.sax.SAXParseException;

import com.demo.iapps.exception.InvalidFilterTypeException;
import com.demo.iapps.exception.NoRecordFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = SAXParseException.class)
	public ResponseEntity<Object> handleBadCredentialsException(SAXParseException ex){
		return ResponseEntity.status(400).body(ex.getMessage());
	}
	
	@ExceptionHandler(value = NoRecordFoundException.class)
	public ResponseEntity<Object> handleNoRecordFoundException(NoRecordFoundException ex){
		return ResponseEntity.status(200).body(ex.getMessage());
	}
	
	@ExceptionHandler(value = InvalidFilterTypeException.class)
	public ResponseEntity<Object> handleInvalidFilterTypeException(InvalidFilterTypeException ex){
		return ResponseEntity.status(400).body(ex.getMessage());
	}
	
	@ExceptionHandler(value = NumberFormatException.class)
	public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex){
		return ResponseEntity.status(400).body("Error occured while parsing: "+ex.getMessage()+ " please provide a proper number in path variable");
	}

}
