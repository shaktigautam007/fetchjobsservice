package com.myjobs.jobservice.service;


import com.myjobs.jobservice.exception.JobServiceException;
import com.myjobs.jobservice.model.dto.CombinedData;
import com.myjobs.jobservice.model.enums.JobSource;
import com.myjobs.jobservice.strategy.JobFetchStrategy;
import com.myjobs.jobservice.strategy.JobFetchStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobFetchStrategyFactory strategyFactory;

    @Override
    public CombinedData fetchAllJobs(JobSource source, String authToken, String cookie) {
        log.info("Fetching jobs from source: {}", source);

        JobFetchStrategy strategy = strategyFactory.getStrategy(source);

        try {
            return strategy.fetchJobs(authToken, cookie).join();
        } catch (Exception ex) {
            log.error("Error fetching jobs: {}", ex.getMessage(), ex);
            throw new JobServiceException("Failed to fetch jobs", ex);
        }
    }
}



