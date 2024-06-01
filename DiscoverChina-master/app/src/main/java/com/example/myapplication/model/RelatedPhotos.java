package com.example.myapplication.model;

import java.util.ArrayList;

public class RelatedPhotos {
    private Integer total;
    private ArrayList<Photo> results;

    public RelatedPhotos(Integer total, ArrayList<Photo> results) {
        this.total = total;
        this.results = results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ArrayList<Photo> getResults() {
        return results;
    }

    public void setResults(ArrayList<Photo> results) {
        this.results = results;
    }
}
