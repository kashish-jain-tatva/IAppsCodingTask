package com.demo.iapps.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.demo.iapps.entity.EPaperRequest;
import com.demo.iapps.repository.EPaperRequestRepository;

@ExtendWith(MockitoExtension.class)
public class EPaperRequestServiceTest {
	
	@InjectMocks
	private EPaperRequestService service;
	
	@Mock
	private EPaperRequestRepository repo;
	
	@Test
	public void test_save() {
		EPaperRequest request = new EPaperRequest();
		request.setDpi(160);
		request.setFileName("test.xml");
		request.setHeight(752);
		request.setNewspaperName("a");
		request.setUploadTime(LocalDateTime.now());
		request.setWidth(1280);
		request.setId(1l);
		when(repo.save(any(EPaperRequest.class))).thenReturn(request);
		assertEquals(request, service.save(request));
	}
	
	@Test
	public void test_findAll_descendingTrue() {
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
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findAll(any(Pageable.class))).thenReturn(new PageImpl<EPaperRequest>(list, PageRequest.of(0, 2), 2));
		assertEquals(list,service.findAll(2, 0, "uploadTime", true));
		
	}
	
	@Test
	public void test_findAll_descendingFalse() {
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
		when(repo.findAll(any(Pageable.class))).thenReturn(new PageImpl<EPaperRequest>(list, PageRequest.of(0, 2), 2));
		assertEquals(list,service.findAll(2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_findByNewspaperName_descendingFalse() {
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
		when(repo.findByNewspaperName(anyString(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.findByNewspaperName("a",2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_findByNewspaperName_descendingTrue() {
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
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findByNewspaperName(anyString(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.findByNewspaperName("a",2, 0, "uploadTime", true));
		
	}
	
	@Test
	public void test_findByHeight_descendingFalse() {
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
		when(repo.findByHeight(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.findByHeight(752,2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_findByHeight_descendingTrue() {
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
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findByHeight(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.findByHeight(752,2, 0, "uploadTime", true));
		
	}
	
	@Test
	public void test_findByDpi_descendingFalse() {
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
		when(repo.findByDpi(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.findByDpi(160,2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_findByDpi_descendingTrue() {
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
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findByDpi(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.findByDpi(160,2, 0, "uploadTime", true));
		
	}
	
	@Test
	public void test_findByWidth_descendingFalse() {
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
		when(repo.findByWidth(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.findByWidth(1280,2, 0, "uploadTime", false));
		
	}
	
	@Test
	public void test_findByWidth_descendingTrue() {
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
		Collections.sort(list, (r1,r2) -> r2.getUploadTime().compareTo(r1.getUploadTime()));
		when(repo.findByWidth(anyInt(), any(Pageable.class))).thenReturn(list);
		assertEquals(list,service.findByWidth(1280,2, 0, "uploadTime", true));
		
	}

}
