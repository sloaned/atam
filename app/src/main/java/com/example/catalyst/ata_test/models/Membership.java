package com.example.catalyst.ata_test.models;

import java.util.Date;

/**
 * Created by dsloane on 4/22/2016.
 */
public class Membership {
    private int teamId;
    private int userId;
    private boolean active;
    private Date addedOn;
    private Date removedOn;

    public Membership() {}

    public Membership(int teamId, int userId, boolean active) {
        this.teamId = teamId;
        this.userId = userId;
        this.active = active;
    }

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
