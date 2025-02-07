package com.myjobs.jobservice.strategy;

import com.myjobs.jobservice.model.dto.CombinedData;
import java.util.concurrent.CompletableFuture;

public interface JobFetchStrategy {
    CompletableFuture<CombinedData> fetchJobs(String authToken, String cookie);
}