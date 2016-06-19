package com.example.catalyst.ata_test.models;

/**
 * Created by dsloane on 6/1/2016.
 */
public class SearchResult {

    //Holds the user information from search result.
    private User user;

    //Holds the team information fromt he search result.
    private Team team;

    //Constructor
    public SearchResult() {}
    public SearchResult(User user, Team team) {
        this.user = user;
        this.team = team;
    }

    //Getters and setters
    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}
    public void setTeam(Team team) {this.team = team;}
    public Team getTeam() {return team;}
}
