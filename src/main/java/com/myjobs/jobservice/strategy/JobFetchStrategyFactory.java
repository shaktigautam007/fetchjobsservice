package com.myjobs.jobservice.strategy;


import com.myjobs.jobservice.model.enums.JobSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JobFetchStrategyFactory {
    private final Map<JobSource, JobFetchStrategy> strategies;

    @Autowired
    public JobFetchStrategyFactory(List<JobFetchStrategy> strategyList) {
        strategies = new EnumMap<>(JobSource.class);
        strategies.put(JobSource.LINKEDIN, findStrategy(strategyList, LinkedInJobFetchStrategy.class));
        strategies.put(JobSource.INDEED, findStrategy(strategyList, IndeedJobFetchStrategy.class));
    }

    public JobFetchStrategy getStrategy(JobSource source) {
        return Optional.ofNullable(strategies.get(source))
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported search portal : " + source));
    }

    private JobFetchStrategy findStrategy(List<JobFetchStrategy> strategies, Class<?> strategyClass) {
        return strategies.stream()
                .filter(strategy -> strategy.getClass().equals(strategyClass))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("This source  not supported yet: " + strategyClass));
    }
}