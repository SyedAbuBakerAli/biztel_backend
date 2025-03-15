package com.biztel.assignment.service;

import org.springframework.http.ResponseEntity;

public interface CrawlService {

    public Long startCrawl(String url);

    public ResponseEntity<?> fetchCrawlResult(Long id);
}
