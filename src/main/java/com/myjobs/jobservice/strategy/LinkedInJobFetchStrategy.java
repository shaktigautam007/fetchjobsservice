package com.myjobs.jobservice.strategy;


import com.myjobs.jobservice.exception.JobServiceException;
import com.myjobs.jobservice.mapper.ResponseMapper;
import com.myjobs.jobservice.model.dto.CombinedData;
import com.myjobs.jobservice.model.dto.SearchResultData;
import com.myjobs.jobservice.model.enums.JobType;
import com.myjobs.jobservice.util.Constants;
import com.myjobs.jobservice.util.HeadersBuilder;
import com.myjobs.jobservice.util.UriBuilder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkedInJobFetchStrategy implements JobFetchStrategy {

    private final WebClient.Builder webClientBuilder;

    @Override
    @CircuitBreaker(name = "linkedinService", fallbackMethod = "fallbackFetchJobs")
    @Retry(name = "linkedinService", fallbackMethod = "fallbackFetchJobs")
    public CompletableFuture<CombinedData> fetchJobs(String authToken, String cookie) {
        log.info("Starting job fetch process from LinkedIn...");
        WebClient webClient = createWebClient();
        CompletableFuture<SearchResultData> savedJobs = fetchJobsByType(webClient, JobType.SAVED, authToken, cookie);
        CompletableFuture<SearchResultData> appliedJobs = fetchJobsByType(webClient, JobType.APPLIED, authToken, cookie);
        CompletableFuture<SearchResultData> archivedJobs = fetchJobsByType(webClient, JobType.ARCHIVED, authToken, cookie);
        CompletableFuture<SearchResultData> inProgressJobs = fetchJobsByType(webClient, JobType.IN_PROGRESS, authToken, cookie);
        log.info("job fetch submitted for all statuses.");
        return combineResults(savedJobs, appliedJobs, archivedJobs,inProgressJobs);
    }


    private CompletableFuture<CombinedData> combineResults(
            CompletableFuture<SearchResultData> savedJobs,
            CompletableFuture<SearchResultData> appliedJobs,
            CompletableFuture<SearchResultData> archivedJobs,
            CompletableFuture<SearchResultData> inProgressJobs) {

        log.info("Combining job search results...");

        return CompletableFuture.allOf(savedJobs, appliedJobs, archivedJobs, inProgressJobs)
                .thenApply(v -> {
                    try {
                        CombinedData combinedData = new CombinedData();
                        combinedData.setSaved(ResponseMapper.mapToClientResponse(savedJobs.join()));
                        combinedData.setApplied(ResponseMapper.mapToClientResponse(appliedJobs.join()));
                        combinedData.setArchived(ResponseMapper.mapToClientResponse(archivedJobs.join()));
                        combinedData.setInProgress(ResponseMapper.mapToClientResponse(inProgressJobs.join()));
                        return combinedData;
                    } catch (CompletionException ex) {
                        log.error("Failed to combine job results: {}", ex.getMessage(), ex);
                        throw new JobServiceException("Failed to cobine job results", ex.getCause());
                    }
                });
    }




    private WebClient createWebClient() {
        log.info("Creating WebClient instance for LinkedIn API...");
        return webClientBuilder
                .baseUrl(Constants.API_BASE_URL)
                .build();
    }


    private CompletableFuture<SearchResultData> fetchJobsByType(
            WebClient webClient, JobType type, String authToken, String cookie) {
        String uri = UriBuilder.buildJobsUri(type);
        log.info("Fetching jobs for type: {}, URI: {}", type, uri);

        return webClient.get()
                .uri(uri)
                .headers(headers -> {
                    HeadersBuilder.setHeaders(headers, authToken, cookie);
                    log.info("Headers for job type {}: {}", type, headers);
                })
                .retrieve()
                .bodyToMono(SearchResultData.class)
                .timeout(Duration.ofSeconds(30)) // linkedin apis are slow so increased timeout
                .toFuture()
                .exceptionally(ex -> {
                    log.error("Error fetching jobs for type {}: {}", type, ex.getMessage(), ex);
                    return new SearchResultData();
                });
    }

    // Fallback method for Circuit Breaker and Retry
    public CompletableFuture<CombinedData> fallbackFetchJobs(String authToken, String cookie, Throwable ex) {
        log.error("Fallback: LinkedIn service is down or failed. Returning empty job data.");
        CombinedData fallbackData = new CombinedData();
        fallbackData.setSaved(null);
        fallbackData.setApplied(null);
        fallbackData.setArchived(null);
        fallbackData.setInProgress(null);
        return CompletableFuture.completedFuture(fallbackData);
    }


}
