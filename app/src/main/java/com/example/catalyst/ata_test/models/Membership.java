package com.example.catalyst.ata_test.models;

import java.util.Date;

/**
 * Created by dsloane on 4/22/2016.
 *
 */
public class Membership {

    //teamId represents the team that
    //that Membership object is a part of.
    private int teamId;

    //User attached to this Membership object.
    private int userId;

    //Is the memeber active in the team?
    private boolean active;

    //The date the member was created
    //Do be a part of the team.
    private Date addedOn;

    //The date the member was removed from the team.
    private Date removedOn;

    //Constructors.
    public Membership() {}
    public Membership(int teamId, int userId, boolean active) {
        this.teamId = teamId;
        this.userId = userId;
        this.active = active;
    }

    //Getters and setters
    public int getUserId() {return userId;}
    public void setUserId(int id) {userId = id;}
    public int getTeamId() {return teamId;}
    public void setTeamId(int id) {teamId = id;}
    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}
    public Date getAddedOn() {return addedOn;}
    public void setAddedOn(Date addedOn) {this.addedOn = addedOn;}
    public Date getRemovedOn() {return removedOn;}
    public void setRemovedOn(Date removedOn) {this.removedOn = removedOn;}


}
