package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.Team;

/**
 * Created by dsloane on 5/6/2016.
 */
public class ViewTeamEvent {

    private Team team;

    public ViewTeamEvent(Team team) {
        this.team = team;
    }

    public Team getTeam() {return team;}
}
