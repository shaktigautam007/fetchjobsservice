package com.myjobs.jobservice.service;

import com.myjobs.jobservice.model.dto.CombinedData;
import com.myjobs.jobservice.model.enums.JobSource;

import java.util.concurrent.CompletableFuture;



public interface JobService {
    CombinedData fetchAllJobs(JobSource source, String authToken, String cookie);
}