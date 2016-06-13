package com.example.catalyst.ata_test.models;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/1/2016.
 * This profile object is used to
 * populate the profile page of a
 * user.
 */
public class Profile {

    //The user the profile page
    //will display.
    private User user;

    //This holds a lsit of kudos
    //belonging to a particular user.
    private ArrayList<Kudo> kudos;

    //This holds a list of teams
    //the user is a part of.
    private ArrayList<Team> teams;

    //This holds a list of reviews that
    //the are about the user
    private ArrayList<Review> reviews;

    //Constructors
    public Profile(){}
    public Profile(User user, ArrayList<Kudo> kudos, ArrayList<Team> teams, ArrayList<Review> reviews) {
        this.user = user;
        this.kudos = kudos;
        this.teams = teams;
        this.reviews = reviews;
    }

    //Getters and setters
    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}
    public void setKudos(ArrayList<Kudo> kudos) {this.kudos = kudos;}
    public ArrayList<Kudo> getKudos() {return kudos;}
    public void setTeams(ArrayList<Team> teams) {this.teams = teams;}
    public ArrayList<Team> getTeams() {return teams;}
    public void setReviews(ArrayList<Review> reviews) {this.reviews = reviews;}
    public ArrayList<Review> getReviews() {return reviews;}

}
