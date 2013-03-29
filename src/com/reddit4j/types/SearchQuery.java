package com.reddit4j.types;

public class SearchQuery {

    private String query;
    private SearchSyntaxType syntax;
    private SortType sort;
    private TimePeriodType timePeriod;
    private String after;
    private String before;
    private Integer limit;
    private Boolean restrictSubreddit;
    private String target; // still not sure what this is...    
    

    private Integer count; // this seems to change the search results such that their counting scheme starts right after
                           // this number. Very strange. Ignoring.
    public SearchQuery(String query) {
        this.query = query;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public SearchSyntaxType getSyntax() {
        return syntax;
    }
    public void setSyntax(SearchSyntaxType syntax) {
        this.syntax = syntax;
    }
    public SortType getSort() {
        return sort;
    }
    public void setSort(SortType sort) {
        this.sort = sort;
    }
    public TimePeriodType getTimePeriod() {
        return timePeriod;
    }
    public void setTimePeriod(TimePeriodType timePeriod) {
        this.timePeriod = timePeriod;
    }
    public String getAfter() {
        return after;
    }
    public void setAfter(String after) {
        this.after = after;
    }
    public String getBefore() {
        return before;
    }
    public void setBefore(String before) {
        this.before = before;
    }
    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    public Boolean getRestrictSubreddit() {
        return restrictSubreddit;
    }
    public void setRestrictSubreddit(Boolean restrictSubreddit) {
        this.restrictSubreddit = restrictSubreddit;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    

}
