package com.myjobs.jobservice.service;

import com.myjobs.jobservice.exception.JobServiceException;
import com.myjobs.jobservice.model.dto.CombinedData;
import com.myjobs.jobservice.model.enums.JobSource;
import com.myjobs.jobservice.strategy.JobFetchStrategy;
import com.myjobs.jobservice.strategy.JobFetchStrategyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @Mock
    private JobFetchStrategyFactory strategyFactory;

    @Mock
    private JobFetchStrategy jobFetchStrategy;

    @InjectMocks
    private JobServiceImpl jobService;

    private final String authToken = "mockToken";
    private final String cookie = "mockCookie";
    private final JobSource source = JobSource.LINKEDIN;

    @Test
    void fetchAllJobs_Success() {

        CombinedData combinedData = new CombinedData();
        CompletableFuture<CombinedData> futureCombined  = CompletableFuture.completedFuture(combinedData);

        when(strategyFactory.getStrategy(source)).thenReturn(jobFetchStrategy);
        when(jobFetchStrategy.fetchJobs(authToken, cookie)).thenReturn(futureCombined);
        CombinedData result = jobService.fetchAllJobs(source, authToken, cookie);
        assertNotNull(result);
        assertEquals(combinedData, result);

    }

    @Test
    void fetchAllJobs_Failure() {

        CompletableFuture<CombinedData> failed = CompletableFuture.failedFuture(new RuntimeException("Fetch failed"));

        when(strategyFactory.getStrategy(source)).thenReturn(jobFetchStrategy);
        when(jobFetchStrategy.fetchJobs(authToken, cookie)).thenReturn(failed);

        JobServiceException exception = assertThrows(JobServiceException.class,
                () -> jobService.fetchAllJobs(source, authToken, cookie));

        assertEquals("Failed to fetch jobs", exception.getMessage());

    }

    @Test
    void fetchAllJobs_InvalidSource() {

        when(strategyFactory.getStrategy(source))
                .thenThrow(new IllegalArgumentException("Invalid source"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> jobService.fetchAllJobs(source, authToken, cookie));
        assertEquals("Invalid source", exception.getMessage());

    }
}
