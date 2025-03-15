package com.biztel.assignment;

import com.biztel.assignment.entity.CrawlEntity;
import com.biztel.assignment.entity.CrawlStatus;
import com.biztel.assignment.repository.CrawlRepository;
import com.biztel.assignment.service.serviceImpl.CrawlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CrawlServiceImplTest {

    @Mock
    private CrawlRepository crawlRepository;

    @InjectMocks
    private CrawlServiceImpl crawlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchCrawlResult_Success() {
        CrawlEntity crawlEntity = new CrawlEntity();
        crawlEntity.setId(1L);
        crawlEntity.setSeedUrl("https://example.com");
        crawlEntity.setStatus(CrawlStatus.COMPLETED);
        crawlEntity.setCreatedAt(LocalDateTime.now());

        when(crawlRepository.findById(1L)).thenReturn(Optional.of(crawlEntity));

        ResponseEntity<?> response = crawlService.fetchCrawlResult(1L);

        assertEquals(200, response.getStatusCodeValue());
    }


}
