package com.demo.iapps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.iapps.entity.EPaperRequest;
import com.demo.iapps.repository.EPaperRequestRepository;

@Service
public class EPaperRequestService {
	
	private final EPaperRequestRepository repo;
	
	@Autowired
	public EPaperRequestService(EPaperRequestRepository repo) {
		this.repo = repo;
	}
	
	public EPaperRequest save(EPaperRequest request) {
		return repo.save(request);
	}
	
	public List<EPaperRequest> findAll(int pageSize, int pageNumber, String sort, boolean descending){
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		return repo.findAll(PageRequest.of(pageNumber, pageSize, sortObj)).toList();
	}
	
	public List<EPaperRequest> findByNewspaperName(String newspaperName, int pageSize, int pageNumber, String sort, boolean descending){
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		return repo.findByNewspaperName(newspaperName, PageRequest.of(pageNumber, pageSize,sortObj));
	}
	
	public List<EPaperRequest> findByHeight(int height, int pageSize, int pageNumber, String sort, boolean descending){
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		return repo.findByHeight(height, PageRequest.of(pageNumber, pageSize,sortObj));
	}
	
	public List<EPaperRequest> findByDpi(int dpi, int pageSize, int pageNumber, String sort, boolean descending){
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		return repo.findByDpi(dpi, PageRequest.of(pageNumber, pageSize,sortObj));
	}
	
	public List<EPaperRequest> findByWidth(int width, int pageSize, int pageNumber, String sort, boolean descending){
		Sort sortObj = Sort.by(sort);
		if(descending)
			sortObj = sortObj.descending();
		return repo.findByWidth(width, PageRequest.of(pageNumber, pageSize,sortObj));
	}

}
