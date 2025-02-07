package com.myjobs.jobservice.strategy;

import com.myjobs.jobservice.model.dto.CombinedData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;




@Component
@Slf4j
@RequiredArgsConstructor
public class IndeedJobFetchStrategy implements JobFetchStrategy {

    private final WebClient.Builder webClientBuilder;

    @Override
    public CompletableFuture<CombinedData> fetchJobs(String authToken, String cookie) {
        log.info("Fetching jobs from Indeed Job Site");
        return new CompletableFuture<>();
    }
}

