package com.myjobs.jobservice.model.dto;


import java.util.ArrayList;
import java.util.List;

public class SearchResultData {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void addAll(SearchResultData other) {
        if (other == null || other.getData() == null || other.getData().getSearchDashClustersByAll() == null) {
            return; // Nothing to merge
        }

        if (this.data == null) {
            this.data = new Data();
        }

        if (this.data.getSearchDashClustersByAll() == null) {
            this.data.setSearchDashClustersByAll(new SearchDashClustersByAll());
        }

        if (this.data.getSearchDashClustersByAll().getElements() == null) {
            this.data.getSearchDashClustersByAll().setElements(new ArrayList<>());
        }

        List<Elements> thisElements = this.data.getSearchDashClustersByAll().getElements();
        List<Elements> otherElements = other.getData().getSearchDashClustersByAll().getElements();

        if (otherElements != null) {
            thisElements.addAll(otherElements);
        }
    }

    public static class Data {
        private SearchDashClustersByAll searchDashClustersByAll;

        public SearchDashClustersByAll getSearchDashClustersByAll() {
            return searchDashClustersByAll;
        }

        public void setSearchDashClustersByAll(SearchDashClustersByAll searchDashClustersByAll) {
            this.searchDashClustersByAll = searchDashClustersByAll;
        }
    }

    public static class SearchDashClustersByAll {
        private List<Elements> elements;

        public List<Elements> getElements() {
            return elements;
        }

        public void setElements(List<Elements> elements) {
            this.elements = elements;
        }
    }

    public static class Elements {
        private List<Items> items;

        public List<Items> getItems() {
            return items;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }
    }

    public static class Items {
        private Item item;

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    public static class Item {
        private EntityResult entityResult;

        public EntityResult getEntityResult() {
            return entityResult;
        }

        public void setEntityResult(EntityResult entityResult) {
            this.entityResult = entityResult;
        }
    }

    public static class InsightsResolutionResult{
        public SimpleInsight getSimpleInsight() {
            return simpleInsight;
        }

        public void setSimpleInsight(SimpleInsight simpleInsight) {
            this.simpleInsight = simpleInsight;
        }

        public SimpleInsight simpleInsight;

    }

    public static class SimpleInsight{

        public Title title;

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }
    }

    public static class EntityResult {


        private Title title;
        private List<InsightsResolutionResult> insightsResolutionResults;
        private PrimarySubtitle primarySubtitle;
        private SecondarySubtitle secondarySubtitle;

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public PrimarySubtitle getPrimarySubtitle() {
            return primarySubtitle;
        }

        public void setPrimarySubtitle(PrimarySubtitle primarySubtitle) {
            this.primarySubtitle = primarySubtitle;
        }

        public SecondarySubtitle getSecondarySubtitle() {
            return secondarySubtitle;
        }

        public void setSecondarySubtitle(SecondarySubtitle secondarySubtitle) {
            this.secondarySubtitle = secondarySubtitle;
        }

        public List<InsightsResolutionResult> getInsightsResolutionResults() {
            return insightsResolutionResults;
        }

        public void setInsightsResolutionResults(List<InsightsResolutionResult> insightsResolutionResults) {
            this.insightsResolutionResults = insightsResolutionResults;
        }
    }

    public static class Title {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class PrimarySubtitle {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class SecondarySubtitle {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}