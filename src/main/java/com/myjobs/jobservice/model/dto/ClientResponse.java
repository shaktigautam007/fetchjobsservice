package com.myjobs.jobservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ClientResponse {
    private List<Job> jobs;

    @Data
    @AllArgsConstructor
    public static class Job {
        private String role;
        private String company;
        private String location;
        private String appliedOn;

    }
}