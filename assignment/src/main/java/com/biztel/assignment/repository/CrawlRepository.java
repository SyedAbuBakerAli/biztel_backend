package com.biztel.assignment.repository;

import com.biztel.assignment.entity.CrawlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlRepository extends JpaRepository<CrawlEntity,Long> {
}
