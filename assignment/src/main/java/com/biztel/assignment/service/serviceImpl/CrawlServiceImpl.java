package com.biztel.assignment.service.serviceImpl;

import com.biztel.assignment.dto.CrawlResultDTO;
import com.biztel.assignment.entity.CrawlEntity;
import com.biztel.assignment.entity.CrawlStatus;
import com.biztel.assignment.exceptionHandling.CustomExceptions.DatabaseAccessException;
import com.biztel.assignment.repository.CrawlRepository;
import com.biztel.assignment.service.CrawlService;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;

@Service
public class CrawlServiceImpl implements CrawlService {

    private final CrawlRepository crawlRepository;
    private final Executor executor;

    public CrawlServiceImpl(CrawlRepository crawlRepository) {
        this.crawlRepository = crawlRepository;
        this.executor = createExecutor();
    }

    private Executor createExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.initialize();
        return executor;
    }

    @Transactional
    public Long startCrawl(String url) throws DatabaseAccessException{
        try{
            CrawlEntity crawlResult = new CrawlEntity();
            crawlResult.setSeedUrl(url);
            crawlResult.setStatus(CrawlStatus.IN_PROGRESS);
            crawlResult.setCreatedAt(LocalDateTime.now());
            crawlResult = crawlRepository.save(crawlResult);

            final Long crawlId = crawlResult.getId();

            executor.execute(() -> fetchLinks(url, crawlId));

            return crawlId;
        }catch (Exception e){
            throw new DatabaseAccessException("Failed to start crawl job");
        }
    }

    private void fetchLinks(String url, Long crawlId) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            Set<String> urlList = new HashSet<>();

            for (Element link : links) {
                urlList.add(link.absUrl("href"));
            }

            CrawlEntity crawlResult = crawlRepository.findById(crawlId).orElseThrow();
            crawlResult.setCrawledUrls(urlList.stream().toList());
            crawlResult.setStatus(CrawlStatus.COMPLETED);
            crawlRepository.save(crawlResult);

        } catch (Exception e) {
            CrawlEntity crawlResult = crawlRepository.findById(crawlId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid crawl ID: " + crawlId));
            crawlResult.setStatus(CrawlStatus.FAILED);
            crawlRepository.save(crawlResult);
            throw new RuntimeException("Crawling failed for URL: " + url, e);
        }
    }
    public ResponseEntity<?> fetchCrawlResult(Long id) throws DatabaseAccessException{
        try {
            CrawlEntity crawlEntity = crawlRepository.findById(id)
                    .orElseThrow(() -> new DatabaseAccessException("Crawl job with ID " + id + " not found"));

            CrawlResultDTO crawlResultDTO = convertToDTO(crawlEntity);

            if (crawlEntity.getStatus() == CrawlStatus.FAILED) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(crawlResultDTO);
            }

            return ResponseEntity.ok(crawlResultDTO);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }

    private CrawlResultDTO convertToDTO(CrawlEntity crawlEntity) {
        return new CrawlResultDTO(
                crawlEntity.getId(),
                crawlEntity.getSeedUrl(),
                crawlEntity.getCrawledUrls(),
                crawlEntity.getStatus(),
                crawlEntity.getCreatedAt()
        );
    }

}
