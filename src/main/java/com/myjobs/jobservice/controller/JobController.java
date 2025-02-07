package com.myjobs.jobservice.controller;


import com.myjobs.jobservice.model.dto.CombinedData;
import com.myjobs.jobservice.model.enums.JobSource;
import com.myjobs.jobservice.service.JobService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.myjobs.jobservice.util.Constants.RATE_LIMIT_EXCEEDED_ERROR_MSG;


@RestController
@RequestMapping("/api/v1/jobs")
@Tag(name = "Jobs Data API")
@Slf4j
@RequiredArgsConstructor
public class JobController {


    private final JobService jobService;

    @GetMapping
    @RateLimiter(name = "jobServiceRateLimiter", fallbackMethod = "jobServiceFallbackMethod")
    public ResponseEntity<CombinedData> fetchJobs(
            @RequestParam JobSource source,
            @RequestHeader("x-csrf-token") String authToken,
            @RequestHeader("cookie") String cookie) {

        log.info("Fetching jobs from source: {}", source);
        CombinedData combinedData = jobService.fetchAllJobs(source, authToken, cookie);
        log.info("Succesfully fetched job data from soruce : {}",source);
        return ResponseEntity.ok(combinedData);

    }


    public ResponseEntity<String> jobServiceFallbackMethod(Exception ex) {
        return new ResponseEntity<>(RATE_LIMIT_EXCEEDED_ERROR_MSG,HttpStatus.TOO_MANY_REQUESTS);
    }




}