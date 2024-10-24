package com.demo.iapps.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.demo.iapps.data.FilterType;
import com.demo.iapps.entity.EPaperRequest;
import com.demo.iapps.exception.InvalidFilterTypeException;
import com.demo.iapps.exception.NoRecordFoundException;
import com.demo.iapps.repository.EPaperRequestRepository;

@ExtendWith(MockitoExtension.class)
public class EPaperRequestServiceTest {
	
	@InjectMocks
	private EPaperRequestService service;
	
	@Mock
	private EPaperRequestRepository repo;
	
	@Mock
	private XMLService xmlService;
	
	@Mock
	private ResourceLoader resourceLoader;
	
	@Mock
	private Resource resource;
	
	@Test
	public void test_save() throws Exception {
		EPaperRequest request = new EPaperRequest();
		request.setDpi(160);
		request.setFileName("test.xml");
		request.setHeight(752);
		request.setNewspaperName("a");
		request.setUploadTime(LocalDateTime.now());
		request.setWidth(1280);
		request.setId(1l);
		File file = mock(File.class);
		when(file.getName()).thenReturn("mockfile.xml");
		 MockMultipartFile mockFile = new MockMultipartFile("file", file.getName(), "application/xml",getClass().getResourceAsStream("/correct.xml") );
		when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(getClass().getResourceAsStream("/schema.xsd"));
        when(xmlService.parseXML(any(File.class))).thenReturn(request);
		when(repo.save(any(EPaperRequest.class))).thenReturn(request);
		assertEquals(request, service.save(mockFile));
	}
	
	  
	@Test
	public void test_findAll_descendingTrue() throws NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findAll(any(Pageable.class))).thenReturn(new PageImpl<EPaperRequest>(list, PageRequest.of(0, 2), 2));
		assertEquals(list,service.findAll(2, 0, "uploadTime", true));
		
	}
	
	@Test
	public void test_findAll_descendingFalse() throws NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		when(repo.findAll(any(Pageable.class))).thenReturn(new PageImpl<EPaperRequest>(list, PageRequest.of(0, 2), 2));
		assertEquals(list,service.findAll(2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_find_newspaperName_descendingFalse() throws InvalidFilterTypeException, NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		when(repo.findByNewspaperName(anyString(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.find(FilterType.NEWSPAPERNAME.name(),"a",2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_find_newspaperName_descendingTrue() throws InvalidFilterTypeException, NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findByNewspaperName(anyString(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.find(FilterType.NEWSPAPERNAME.name(),"a",2, 0, "uploadTime", true));
		
	}
	
	@Test
	public void test_find_height_descendingFalse() throws InvalidFilterTypeException, NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		when(repo.findByHeight(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.find(FilterType.HEIGHT.name(),"752",2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_find_height_descendingTrue() throws InvalidFilterTypeException, NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findByHeight(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.find(FilterType.HEIGHT.name(),"752",2, 0, "uploadTime", true));
		
	}
	
	@Test
	public void test_find_dpi_descendingFalse() throws InvalidFilterTypeException, NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		when(repo.findByDpi(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.find(FilterType.DPI.name(),"160",2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_find_dpi_descendingTrue() throws InvalidFilterTypeException, NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findByDpi(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.find(FilterType.DPI.name(),"160",2, 0, "uploadTime", true));
		
	}
	
	@Test
	public void test_find_width_descendingFalse() throws InvalidFilterTypeException, NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		when(repo.findByWidth(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.find(FilterType.WIDTH.name(),"1280",2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_find_width_descendingTrue() throws InvalidFilterTypeException, NoRecordFoundException {
		List<EPaperRequest> list = prepareListData();
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findByWidth(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.find(FilterType.WIDTH.name(),"1280",2, 0, "uploadTime", true));
		
	}
	
	@Test
	public void test_find_InvalidFilterTypeException(){
		assertThrows(InvalidFilterTypeException.class, () -> service.find("invalid","1280",2, 0, "uploadTime", true));
	}
	
	@Test
	public void test_findAll_listEmpty_NoRecordFoundException(){
		when(repo.findAll(any(Pageable.class))).thenReturn(new PageImpl<EPaperRequest>(new ArrayList<>(), PageRequest.of(0, 2), 2));
		assertThrows(NoRecordFoundException.class, () -> service.findAll(2, 0, "uploadTime", false));
	}
	
	private List<EPaperRequest> prepareListData(){
		List<EPaperRequest> list = new ArrayList<>();
		EPaperRequest request = new EPaperRequest();
		request.setDpi(160);
		request.setFileName("test.xml");
		request.setHeight(752);
		request.setNewspaperName("a");
		request.setUploadTime(LocalDateTime.now());
		request.setWidth(1280);
		request.setId(1l);
		list.add(request);
		request = new EPaperRequest();
		request.setDpi(160);
		request.setFileName("test.xml");
		request.setHeight(752);
		request.setNewspaperName("a");
		request.setUploadTime(LocalDateTime.now());
		request.setWidth(1280);
		request.setId(2l);
		list.add(request);
		return list;
	}

}
