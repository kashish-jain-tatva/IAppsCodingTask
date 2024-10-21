package com.demo.iapps.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.demo.iapps.service.EPaperRequestService;
import com.demo.iapps.service.XMLService;

@RestController
@RequestMapping("/api/epaper")
public class EPaperRequestController {
	
	private final EPaperRequestService service;
	private final XMLService xmlService;
	
	@Autowired
	public EPaperRequestController(EPaperRequestService service,XMLService xmlService) {
		this.service = service;
		this.xmlService = xmlService;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestPart(name = "file") MultipartFile file) throws Exception{
		File xmlFile = File.createTempFile(file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".xml") + 1 ), ".xml");
        file.transferTo(xmlFile);
        // Validate XML
        File xsdFile = new File("src/main/resources/schema.xsd");
        xmlService.validateXml(xmlFile, xsdFile);
        EPaperRequest request = xmlService.parseXML(xmlFile);
        service.save(request);
		return ResponseEntity.ok("File uploaded successfully");
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<EPaperRequest>> getAll(@RequestParam(name = "pageSize", defaultValue = "10") int pageSize, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name = "sort", defaultValue = "uploadTime") String sort, @RequestParam(name = "descending", defaultValue = "false") boolean descending){
		return ResponseEntity.ok(service.findAll(pageSize,pageNumber,sort,descending));
	}
	
	@GetMapping("/getByWidth/{width}")
	public ResponseEntity<List<EPaperRequest>> getByWidth(@PathVariable("width") int width, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name = "sort", defaultValue = "uploadTime") String sort, @RequestParam(name = "descending", defaultValue = "false") boolean descending){
		return ResponseEntity.ok(service.findByWidth(width, pageSize, pageNumber, sort, descending));
	}
	
	@GetMapping("/getByHeight/{height}")
	public ResponseEntity<List<EPaperRequest>> getByHeight(@PathVariable("height") int height, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name = "sort", defaultValue = "uploadTime") String sort, @RequestParam(name = "descending", defaultValue = "false") boolean descending){
		return ResponseEntity.ok(service.findByHeight(height, pageSize, pageNumber, sort, descending));
	}
	
	@GetMapping("/getByDpi/{dpi}")
	public ResponseEntity<List<EPaperRequest>> getByDpi(@PathVariable("dpi") int dpi, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name = "sort", defaultValue = "uploadTime") String sort, @RequestParam(name = "descending", defaultValue = "false") boolean descending){
		return ResponseEntity.ok(service.findByDpi(dpi, pageSize, pageNumber, sort, descending));
	}
	
	@GetMapping("/getByNewspaperName/{name}")
	public ResponseEntity<List<EPaperRequest>> getByNewspaperName(@PathVariable("name") String name, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name = "sort", defaultValue = "uploadTime") String sort, @RequestParam(name = "descending", defaultValue = "false") boolean descending){
		return ResponseEntity.ok(service.findByNewspaperName(name, pageSize, pageNumber, sort, descending));
	}
	

}
