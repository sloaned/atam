package com.example.catalyst.ata_test.models;

/**
 * Created by dsloane on 6/1/2016.
 */
public class SearchResult {

    private User user;
    private Team team;

    public SearchResult() {}

    public SearchResult(User user, Team team) {
        this.user = user;
        this.team = team;
    }

    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}
    public void setTeam(Team team) {this.team = team;}
    public Team getTeam() {return team;}
}
