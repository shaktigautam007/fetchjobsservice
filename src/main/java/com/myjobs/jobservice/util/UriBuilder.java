package com.myjobs.jobservice.util;


import com.myjobs.jobservice.model.enums.JobType;
import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponentsBuilder;

@UtilityClass
public class UriBuilder {
    public static String buildJobsUri(JobType type) {
        return UriComponentsBuilder.fromPath("")
                .queryParam("variables", buildQueryVariables(type))
                .queryParam("queryId", Constants.QUERY_ID)
                .build()
                .toUriString();
    }

    private static String buildQueryVariables(JobType type) {
        return String.format("(start:0,query:(flagshipSearchIntent:SEARCH_MY_ITEMS_JOB_SEEKER," +
                "queryParameters:List((key:cardType,value:List(%s)))))", type.name());
    }
}