package com.myjobs.jobservice.mapper;

import com.myjobs.jobservice.model.dto.SearchResultData;
import com.myjobs.jobservice.model.dto.ClientResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResponseMapper {


    public static ClientResponse mapToClientResponse(SearchResultData response) {


        if (response == null || response.getData() == null
                || response.getData().getSearchDashClustersByAll() == null
                || response.getData().getSearchDashClustersByAll().getElements() == null) {
            return new ClientResponse();
        }

        List<ClientResponse.Job> jobs = new ArrayList<>();

        for (SearchResultData.Elements element : response.getData().getSearchDashClustersByAll().getElements()) {
            if (element.getItems() != null) {
                for (SearchResultData.Items item : element.getItems()) {
                    SearchResultData.Item jobItem = item.getItem();
                    if (jobItem != null && jobItem.getEntityResult() != null) {

                        SearchResultData.EntityResult entityResult = jobItem.getEntityResult();

                        String role = Optional.ofNullable(entityResult)
                                .map(SearchResultData.EntityResult::getTitle)
                                .map(SearchResultData.Title::getText)
                                .orElse(null);

                        String company = Optional.ofNullable(entityResult)
                                .map(SearchResultData.EntityResult::getPrimarySubtitle)
                                .map(SearchResultData.PrimarySubtitle::getText)
                                .orElse(null);

                        String location = Optional.ofNullable(entityResult)
                                .map(SearchResultData.EntityResult::getSecondarySubtitle)
                                .map(SearchResultData.SecondarySubtitle::getText)
                                .orElse(null);


                        String applicationDetails = Optional.ofNullable(entityResult)
                                .map(SearchResultData.EntityResult::getInsightsResolutionResults)
                                .filter(list -> !list.isEmpty())
                                .map(list -> list.get(0))
                                .map(SearchResultData.InsightsResolutionResult::getSimpleInsight)
                                .map(SearchResultData.SimpleInsight::getTitle)
                                .map(SearchResultData.Title::getText)
                                .orElse(null);

                        ClientResponse.Job job = new ClientResponse.Job(role, company, location,applicationDetails);
                        jobs.add(job);
                    }
                }
            }
        }

        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setJobs(jobs);
        return clientResponse;
    }





}
