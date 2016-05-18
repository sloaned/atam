package com.example.catalyst.ata_test.events;

import android.util.Log;

import com.example.catalyst.ata_test.models.Team;

import java.util.ArrayList;

/**
 * Created by dsloane on 5/6/2016.
 */
/* Fires when the home/dashboard activity is launched/refreshed */
public class TeamsEvent {
    private final String TAG = TeamsEvent.class.getSimpleName();

    private ArrayList<Team> teams;

    public TeamsEvent(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public ArrayList<Team> getTeams() {return teams;}
}
