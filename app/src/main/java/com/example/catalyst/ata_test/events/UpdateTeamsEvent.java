package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.Team;

import java.util.ArrayList;

/**
 * Created by dsloane on 5/27/2016.
 * This class is used by the EventBus library.
 * This class is used for the Dashboard Fragment.
 */
public class UpdateTeamsEvent {

    //This list holds a list of teams.
    private ArrayList<Team> teams;

    //Constructor.
    public UpdateTeamsEvent(ArrayList<Team> teams) {this.teams = teams;}

    //Basic getter for the team.
    public ArrayList<Team> getTeams() {return teams;}
}
