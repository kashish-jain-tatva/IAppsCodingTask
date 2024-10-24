package com.demo.iapps.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.demo.iapps.data.FilterType;
import com.demo.iapps.entity.EPaperRequest;
import com.demo.iapps.exception.InvalidFilterTypeException;
import com.demo.iapps.exception.InvalidInputFileException;
import com.demo.iapps.exception.InvalidSortValueException;
import com.demo.iapps.exception.NoRecordFoundException;
import com.demo.iapps.repository.EPaperRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EPaperRequestService {

	private final EPaperRequestRepository repo;
	private final XMLService xmlService;
	private final ResourceLoader resourceLoader;

	private static final Logger logger = LoggerFactory.getLogger(EPaperRequestService.class);

	public EPaperRequest save(MultipartFile file)
			throws SAXException, ParserConfigurationException, IOException, InvalidInputFileException {
		// validating if file is an xml and file is not null
		if (file == null || !file.getOriginalFilename().toLowerCase().endsWith(".xml")) {
			logger.error("file is null or file is not an xml file");
			throw new InvalidInputFileException("either file is null or file is not an xml");
		}
		logger.info("save() method started");
		File xmlFile = File.createTempFile(file.getOriginalFilename(), ".xml");
		file.transferTo(xmlFile);
		Resource resource = resourceLoader.getResource("classpath:schema.xsd");
		File xsdFile = File.createTempFile("schema", ".xsd");

		try (InputStream inputStream = resource.getInputStream();
				FileOutputStream outputStream = new FileOutputStream(xsdFile)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}
		// Validate XML
		logger.info("going to validate XML file");
		xmlService.validateXml(xmlFile, xsdFile);
		logger.info("XML file validated successfully");
		logger.info("going to parse XML");
		EPaperRequest request = xmlService.parseXML(xmlFile);
		logger.info("XML parsed successfully");
		request.setFileName(file.getOriginalFilename());
		logger.info("save() method ended");
		return repo.save(request);
	}

	public List<EPaperRequest> findAll(int pageSize, int pageNumber, String sort, boolean descending)
			throws NoRecordFoundException, InvalidSortValueException {
		logger.info("findAll() method started");
		Sort sortObj = prepareAndValidateSortObject(sort, descending);
		List<EPaperRequest> list = repo.findAll(PageRequest.of(pageNumber, pageSize, sortObj)).toList();
		if (list == null || list.size() == 0) {
			logger.info("returned list from repo is null or empty");
			throw new NoRecordFoundException("No Record found");
		}
		logger.info("findAll() method ended with list: " + list);
		return list;
	}

	private List<EPaperRequest> findByNewspaperName(String newspaperName, int pageSize, int pageNumber, String sort,
			boolean descending) throws InvalidSortValueException, NoRecordFoundException {
		Sort sortObj = prepareAndValidateSortObject(sort, descending);
		List<EPaperRequest> list = repo.findByNewspaperName(newspaperName,
				PageRequest.of(pageNumber, pageSize, sortObj));
		if (list == null || list.size() == 0) {
			logger.info("returned list from repo is null or empty");
			throw new NoRecordFoundException("No Record found");
		}
		return list;
	}

	private List<EPaperRequest> findByHeight(int height, int pageSize, int pageNumber, String sort, boolean descending)
			throws InvalidSortValueException, NoRecordFoundException {
		Sort sortObj = prepareAndValidateSortObject(sort, descending);
		List<EPaperRequest> list = repo.findByHeight(height, PageRequest.of(pageNumber, pageSize, sortObj));
		if (list == null || list.size() == 0) {
			logger.info("returned list from repo is null or empty");
			throw new NoRecordFoundException("No Record found");
		}
		return list;
	}

	private List<EPaperRequest> findByDpi(int dpi, int pageSize, int pageNumber, String sort, boolean descending)
			throws InvalidSortValueException, NoRecordFoundException {
		Sort sortObj = prepareAndValidateSortObject(sort, descending);
		List<EPaperRequest> list = repo.findByDpi(dpi, PageRequest.of(pageNumber, pageSize, sortObj));
		if (list == null || list.size() == 0) {
			logger.info("returned list from repo is null or empty");
			throw new NoRecordFoundException("No Record found");
		}
		return list;
	}

	private List<EPaperRequest> findByWidth(int width, int pageSize, int pageNumber, String sort, boolean descending)
			throws InvalidSortValueException, NoRecordFoundException {
		Sort sortObj = prepareAndValidateSortObject(sort, descending);
		List<EPaperRequest> list = repo.findByWidth(width, PageRequest.of(pageNumber, pageSize, sortObj));
		if (list == null || list.size() == 0) {
			logger.info("returned list from repo is null or empty");
			throw new NoRecordFoundException("No Record found");
		}
		return list;
	}

	public List<EPaperRequest> find(String type, String value, int pageSize, int pageNumber, String sort,
			boolean descending) throws InvalidFilterTypeException, NoRecordFoundException, NumberFormatException,
			InvalidSortValueException {
		logger.info("find() method started");
		FilterType filterType;
		try {
			filterType = FilterType.valueOf(type.toUpperCase());
		} catch (Exception e) {
			String possibleFilters = Arrays.stream(FilterType.values()).map(FilterType::getType)
					.collect(Collectors.joining(", "));
			logger.error("invalid type value provided, possible values are: " + possibleFilters);
			throw new InvalidFilterTypeException("Invalid value for type, possible values are: " + possibleFilters);
		}
		List<EPaperRequest> list = null;
		switch (filterType) {
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
		if (list == null || list.size() == 0) {
			logger.info("returned list from repo is null or empty");
			throw new NoRecordFoundException("No Record found");
		}
		logger.info("find() method ended with list: " + list);
		return list;
	}

	private Sort prepareAndValidateSortObject(String sort, boolean descending) throws InvalidSortValueException {
		logger.info("prepareAndValidateSortObject() method started");
		List<String> sortValues = List.of("newspaperName", "width", "height", "dpi", "uploadTime", "fileName");
		if (!sortValues.contains(sort)) {
			logger.error("invalid sort value provided, possible values are: " + sortValues);
			throw new InvalidSortValueException("invalid sort value provided, possible values are: " + sortValues);
		}
		Sort sortObj = Sort.by(sort);
		if (descending)
			sortObj = sortObj.descending();
		logger.info("prepareAndValidateSortObject() method ended");
		return sortObj;
	}

}
