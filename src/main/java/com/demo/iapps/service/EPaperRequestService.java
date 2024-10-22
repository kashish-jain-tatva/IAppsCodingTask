package com.demo.iapps.service;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.iapps.data.FilterType;
import com.demo.iapps.entity.EPaperRequest;
import com.demo.iapps.exception.InvalidFilterTypeException;
import com.demo.iapps.exception.NoRecordFoundException;
import com.demo.iapps.repository.EPaperRequestRepository;

@Service
public class EPaperRequestService {
	
	private final EPaperRequestRepository repo;
	private final XMLService xmlService;
	
	public EPaperRequestService(EPaperRequestRepository repo,XMLService xmlService) {
		this.repo = repo;
		this.xmlService = xmlService;
	}
	
	public EPaperRequest save(MultipartFile file) throws Exception{
		File xmlFile = File.createTempFile(file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".xml") + 1 ), ".xml");
        file.transferTo(xmlFile);
        // Validate XML
        File xsdFile = new File("src/main/resources/schema.xsd");
        xmlService.validateXml(xmlFile, xsdFile);
        EPaperRequest request = xmlService.parseXML(xmlFile);
		return repo.save(request);
	}
	
	public List<EPaperRequest> findAll(int pageSize, int pageNumber, String sort, boolean descending) throws NoRecordFoundException{
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		List<EPaperRequest> list = repo.findAll(PageRequest.of(pageNumber, pageSize, sortObj)).toList();
		if(list == null || list.size() == 0) throw new NoRecordFoundException("No Record found"); 
		return list;
	}
	
	private List<EPaperRequest> findByNewspaperName(String newspaperName, int pageSize, int pageNumber, String sort, boolean descending){
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		return repo.findByNewspaperName(newspaperName, PageRequest.of(pageNumber, pageSize,sortObj));
	}
	
	private List<EPaperRequest> findByHeight(int height, int pageSize, int pageNumber, String sort, boolean descending){
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		return repo.findByHeight(height, PageRequest.of(pageNumber, pageSize,sortObj));
	}
	
	private List<EPaperRequest> findByDpi(int dpi, int pageSize, int pageNumber, String sort, boolean descending){
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		return repo.findByDpi(dpi, PageRequest.of(pageNumber, pageSize,sortObj));
	}
	
	private List<EPaperRequest> findByWidth(int width, int pageSize, int pageNumber, String sort, boolean descending){
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		return repo.findByWidth(width, PageRequest.of(pageNumber, pageSize,sortObj));
	}

	public List<EPaperRequest> find(String type, String value, int pageSize, int pageNumber, String sort, boolean descending) throws InvalidFilterTypeException, NoRecordFoundException {
		FilterType filterType;
		try {
			filterType = FilterType.valueOf(type.toUpperCase());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			FilterType[] values = FilterType.values();
			StringBuilder builder = new StringBuilder();
			for(FilterType filter: values) {
				builder.append(filter.getType() + ", ");
			}
			throw new InvalidFilterTypeException("Invalid value for type, possible values are: "+ builder.substring(0, builder.length() - 2));
		}
		List<EPaperRequest> list = null;
		switch(filterType) {
		case DPI:
			list = findByDpi(Integer.parseInt(value), pageSize, pageNumber, sort, descending);
			break;
		case HEIGHT:
			list = findByHeight(Integer.parseInt(value), pageSize, pageNumber, sort, descending);
			break;
		case NEWSPAPERNAME:
			list = findByNewspaperName(value, pageSize, pageNumber, sort, descending);
			break;
		case WIDTH:
			list = findByWidth(Integer.parseInt(value), pageSize, pageNumber, sort, descending);
			break;
		
		}
		if(list == null || list.size() == 0) throw new NoRecordFoundException("No Record found");
		return list;
	}

}
