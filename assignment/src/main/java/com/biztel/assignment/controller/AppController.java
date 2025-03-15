package com.biztel.assignment.controller;

import com.biztel.assignment.exceptionHandling.CustomExceptions.DatabaseAccessException;
import com.biztel.assignment.repository.CrawlRepository;
import com.biztel.assignment.service.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;


@RestController
@RequestMapping("/crawl")
public class AppController {

    private static final Pattern URL_PATTERN = Pattern.compile("^(http|https)://.*$");

    @Autowired
    public CrawlRepository crawlRepository;

    @Autowired
    public CrawlService crawlService;

    @PostMapping
    public ResponseEntity<String> startCrawl (@RequestParam String url) throws DatabaseAccessException {
        try{
            if (!URL_PATTERN.matcher(url).matches()) {
                return ResponseEntity.badRequest().body("Invalid URL. Must start with http or https.");
            }
            Long jobId = crawlService.startCrawl(url);
            return ResponseEntity.ok("crawl Id:  "+ jobId);
        }catch (Exception e){
            throw  e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCrawlResult (@PathVariable Long id) throws DatabaseAccessException{
        try{
            return crawlService.fetchCrawlResult(id);
        }catch (Exception e){
            throw  e;
        }
    }
}
