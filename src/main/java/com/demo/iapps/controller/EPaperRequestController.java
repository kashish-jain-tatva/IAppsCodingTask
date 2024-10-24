package com.demo.iapps.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.iapps.entity.EPaperRequest;
import com.demo.iapps.exception.InvalidFilterTypeException;
import com.demo.iapps.exception.InvalidSortValueException;
import com.demo.iapps.exception.NoRecordFoundException;
import com.demo.iapps.service.EPaperRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/epaper")
@RequiredArgsConstructor
public class EPaperRequestController {

	private final EPaperRequestService service;

	private static final Logger logger = LoggerFactory.getLogger(EPaperRequestController.class);

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestPart(name = "file") MultipartFile file) throws Exception {
		logger.info("upload api of EPaperRequestController called with file name: " + file.getOriginalFilename());
		service.save(file);
		return ResponseEntity.ok("File uploaded successfully");
	}

	@GetMapping("/get/{type}/{value}")
	public ResponseEntity<List<EPaperRequest>> get(@PathVariable("type") String type,
			@PathVariable("value") String value, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "sort", defaultValue = "uploadTime") String sort,
			@RequestParam(name = "descending", defaultValue = "false") boolean descending)
			throws InvalidFilterTypeException, NoRecordFoundException, NumberFormatException,
			InvalidSortValueException {
		logger.info("get api of EPaperRequestController called with type: " + type + " and value: " + value);
		return ResponseEntity.ok(service.find(type, value, pageSize, pageNumber, sort, descending));
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EPaperRequest>> getAll(
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "sort", defaultValue = "uploadTime") String sort,
			@RequestParam(name = "descending", defaultValue = "false") boolean descending)
			throws NoRecordFoundException, InvalidSortValueException {
		logger.info("getAll of EPaperRequestController api called");
		return ResponseEntity.ok(service.findAll(pageSize, pageNumber, sort, descending));
	}

}
