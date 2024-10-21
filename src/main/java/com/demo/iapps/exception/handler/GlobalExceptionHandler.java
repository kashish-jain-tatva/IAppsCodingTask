package com.demo.iapps.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.xml.sax.SAXParseException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = SAXParseException.class)
	public ResponseEntity<Object> handleBadCredentialsException(SAXParseException ex){
		return ResponseEntity.status(400).body(ex.getMessage());
	} 

}
