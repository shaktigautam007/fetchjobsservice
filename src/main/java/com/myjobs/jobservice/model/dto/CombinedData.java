package com.myjobs.jobservice.model.dto;


import lombok.Data;

import java.util.concurrent.CompletableFuture;

@Data
public class CombinedData {

    private ClientResponse saved;
    private ClientResponse applied;
    private ClientResponse inProgress;
    private ClientResponse archived;

}
