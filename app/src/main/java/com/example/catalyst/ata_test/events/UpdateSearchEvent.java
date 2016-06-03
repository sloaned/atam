package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/2/2016.
 */
public class UpdateSearchEvent {

    private ArrayList<User> users;
    private ArrayList<Team> teams;

    public UpdateSearchEvent(ArrayList<User> users, ArrayList<Team> teams) {
        this.users = users;
        this.teams = teams;
    }

    public void setUsers(ArrayList<User> users) {this.users = users;}
    public ArrayList<User> getUsers() {return users;}
    public void setTeams(ArrayList<Team> teams) {this.teams = teams;}
    public ArrayList<Team> getTeams() {return teams;}
}
