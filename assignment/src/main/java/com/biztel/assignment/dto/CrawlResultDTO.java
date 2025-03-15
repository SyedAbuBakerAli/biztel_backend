package com.biztel.assignment.dto;

import com.biztel.assignment.entity.CrawlStatus;

import java.time.LocalDateTime;
import java.util.List;

public class CrawlResultDTO {

    private Long id;
    private String seedUrl;
    private List<String> crawledUrls;
    private CrawlStatus status;
    private LocalDateTime createdAt;

    public CrawlResultDTO(Long id, String seedUrl, List<String> crawledUrls,
                          CrawlStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.seedUrl = seedUrl;
        this.crawledUrls = crawledUrls;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeedUrl() {
        return seedUrl;
    }

    public void setSeedUrl(String seedUrl) {
        this.seedUrl = seedUrl;
    }

    public List<String> getCrawledUrls() {
        return crawledUrls;
    }

    public void setCrawledUrls(List<String> crawledUrls) {
        this.crawledUrls = crawledUrls;
    }

    public CrawlStatus getStatus() {
        return status;
    }

    public void setStatus(CrawlStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
