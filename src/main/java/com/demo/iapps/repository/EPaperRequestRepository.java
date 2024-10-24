package com.demo.iapps.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.iapps.entity.EPaperRequest;

@Repository
public interface EPaperRequestRepository extends JpaRepository<EPaperRequest, Long> {

	List<EPaperRequest> findByNewspaperName(String newspaperName, Pageable pageable);

	List<EPaperRequest> findByWidth(int width, Pageable pageable);

	List<EPaperRequest> findByHeight(int height, Pageable pageable);

	List<EPaperRequest> findByDpi(int dpi, Pageable pageable);

	List<EPaperRequest> findByUploadTimeBetween(LocalDateTime fromDateTime, LocalDateTime toDateTime,
			Pageable pageable);

	List<EPaperRequest> findByUploadTimeAfter(LocalDateTime fromDateTime, Pageable pageable);

	List<EPaperRequest> findByUploadTimeBefore(LocalDateTime toDateTime, Pageable pageable);

}
