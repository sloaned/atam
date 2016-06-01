package com.example.catalyst.ata_test.models;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/1/2016.
 */
public class Profile {

    private User user;
    private ArrayList<Kudo> kudos;
    private ArrayList<Team> teams;
    private ArrayList<Review> reviews;

    public Profile(){}

    public Profile(User user, ArrayList<Kudo> kudos, ArrayList<Team> teams, ArrayList<Review> reviews) {
        this.user = user;
        this.kudos = kudos;
        this.teams = teams;
        this.reviews = reviews;
    }

    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}
    public void setKudos(ArrayList<Kudo> kudos) {this.kudos = kudos;}
    public ArrayList<Kudo> getKudos() {return kudos;}
    public void setTeams(ArrayList<Team> teams) {this.teams = teams;}
    public ArrayList<Team> getTeams() {return teams;}
    public void setReviews(ArrayList<Review> reviews) {this.reviews = reviews;}
    public ArrayList<Review> getReviews() {return reviews;}

}
