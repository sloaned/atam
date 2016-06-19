package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/2/2016.
 * This class is used by the EventBus library.
 * This class is used for both the TeamSearchTabFragment and the UserSearchTabFragment.
 *
 */
public class UpdateSearchEvent {

    //Holds a list of users to populate the UserSearchTabFragment.
    private ArrayList<User> users;

    //Holds a list of teams to populate the TeamSearachTabFragment.
    private ArrayList<Team> teams;

    //Constructor.
    public UpdateSearchEvent(ArrayList<User> users, ArrayList<Team> teams) {
        this.users = users;
        this.teams = teams;
    }

    //Basic getters and setters.
    public void setUsers(ArrayList<User> users) {this.users = users;}
    public ArrayList<User> getUsers() {return users;}
    public void setTeams(ArrayList<Team> teams) {this.teams = teams;}
    public ArrayList<Team> getTeams() {return teams;}
}
