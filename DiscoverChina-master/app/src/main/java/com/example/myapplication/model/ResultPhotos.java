package com.example.myapplication.model;

import java.util.ArrayList;

public class ResultPhotos {
    private Integer total;
    private Integer total_pages;
    private ArrayList<Photo> results;

    public ResultPhotos(Integer total, Integer total_pages, ArrayList<Photo> results) {
        this.total = total;
        this.total_pages = total_pages;
        this.results = results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<Photo> getResults() {
        return results;
    }

    public void setResults(ArrayList<Photo> results) {
        this.results = results;
    }
}
