package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.Team;

/**
 * Created by dsloane on 5/6/2016.
 * This class is used by the EventBus library.
 */

/* Fires when a user clicks/taps on a specific team */
public class ViewTeamEvent {

    //Holds a single team.
    private Team team;

    //Constuctor.
    public ViewTeamEvent(Team team) {
        this.team = team;
    }

    //Basic getter.
    public Team getTeam() {return team;}
}
