package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.SearchResult;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/1/2016.
 */
public class SearchEvent {

    private ArrayList<SearchResult> results;

    public SearchEvent(ArrayList<SearchResult> results) {this.results = results;}

    public void setResults(ArrayList<SearchResult> results) {this.results = results;}
    public ArrayList<SearchResult> getResults() {return results;}
}
