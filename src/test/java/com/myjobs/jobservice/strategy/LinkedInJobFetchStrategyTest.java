package com.myjobs.jobservice.strategy;

import com.myjobs.jobservice.mapper.ResponseMapper;
import com.myjobs.jobservice.model.dto.CombinedData;
import com.myjobs.jobservice.model.dto.SearchResultData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class LinkedInJobFetchStrategyTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;


    @InjectMocks
    private LinkedInJobFetchStrategy linkedInJobFetchStrategy;

    private final String authToken = "test-auth-token";
    private final String cookie = "test-cookie";





    @Test
    void testCombineResults_Success() {
        SearchResultData savedJobsData = new SearchResultData();
        SearchResultData appliedJobsData = new SearchResultData();
        SearchResultData archivedJobsData = new SearchResultData();
        SearchResultData inProgressJobsData = new SearchResultData();

        CompletableFuture<SearchResultData> savedJobsFuture = CompletableFuture.completedFuture(savedJobsData);
        CompletableFuture<SearchResultData> appliedJobsFuture = CompletableFuture.completedFuture(appliedJobsData);
        CompletableFuture<SearchResultData> archivedJobsFuture = CompletableFuture.completedFuture(archivedJobsData);
        CompletableFuture<SearchResultData> inProgressJobsFuture = CompletableFuture.completedFuture(inProgressJobsData);

        CompletableFuture<CombinedData> result = linkedInJobFetchStrategy.combineResults(
                savedJobsFuture, appliedJobsFuture, archivedJobsFuture, inProgressJobsFuture);

        assertNotNull(result);
        CombinedData combinedData = result.join();
        assertNotNull(combinedData);
        assertEquals(ResponseMapper.mapToClientResponse(savedJobsData), combinedData.getSaved());
        assertEquals(ResponseMapper.mapToClientResponse(appliedJobsData), combinedData.getApplied());
        assertEquals(ResponseMapper.mapToClientResponse(archivedJobsData), combinedData.getArchived());
        assertEquals(ResponseMapper.mapToClientResponse(inProgressJobsData), combinedData.getInProgress());
    }

    @Test
    void testCombineResults_Failure() {
        CompletableFuture<SearchResultData> savedJobsFuture = CompletableFuture.failedFuture(new RuntimeException("Error"));
        CompletableFuture<SearchResultData> appliedJobsFuture = CompletableFuture.completedFuture(new SearchResultData());
        CompletableFuture<SearchResultData> archivedJobsFuture = CompletableFuture.completedFuture(new SearchResultData());
        CompletableFuture<SearchResultData> inProgressJobsFuture = CompletableFuture.completedFuture(new SearchResultData());

        CompletableFuture<CombinedData> result = linkedInJobFetchStrategy.combineResults(
                savedJobsFuture, appliedJobsFuture, archivedJobsFuture, inProgressJobsFuture);

        assertNotNull(result);
        assertThrows(CompletionException.class, result::join);
    }

    @Test
    void testFallbackFetchJobs() {

        CompletableFuture<CombinedData> result = linkedInJobFetchStrategy.fallbackFetchJobs(authToken, cookie, new RuntimeException("Fallback triggered"));

        assertNotNull(result);
        CombinedData combinedData = result.join();
        assertNotNull(combinedData);
        assertNull(combinedData.getSaved());
        assertNull(combinedData.getApplied());
        assertNull(combinedData.getArchived());
        assertNull(combinedData.getInProgress());
    }
}