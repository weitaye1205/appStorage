package com.example.myapplication.model;

import java.util.List;

public class Search {
    private Long total;
    private Long totalPages;
    private List<Photo> results;

    public Search(Long total, Long totalPages, List<Photo> results) {
        this.total = total;
        this.totalPages = totalPages;
        this.results = results;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<Photo> getResults() {
        return results;
    }

    public void setResults(List<Photo> results) {
        this.results = results;
    }
}
