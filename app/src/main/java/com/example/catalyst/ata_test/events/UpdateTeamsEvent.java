package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.Team;

import java.util.ArrayList;

/**
 * Created by dsloane on 5/27/2016.
 */
public class UpdateTeamsEvent {

    private ArrayList<Team> teams;

    public UpdateTeamsEvent(ArrayList<Team> teams) {this.teams = teams;}

    public ArrayList<Team> getTeams() {return teams;}
}
