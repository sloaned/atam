package com.example.catalyst.ata_test.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dsloane on 4/22/2016.
 */
public class Team implements Serializable {
    private String id;
    private String name;
    private boolean active;
    private Assessment assessment;
    private ArrayList<User> memberList;
    private String description;

    public Team() {}

    public Team (String id, String name, boolean active, ArrayList<User> users, String desc) {
        this.id = id;
        this.name = name;
        this.active = active;
        memberList = users;
        description = desc;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}
    public ArrayList<User> getUserList() {return memberList;}
    public void setUserList(ArrayList<User> memberList) {this.memberList = memberList;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Assessment getAssessment() {return assessment;}
    public void setAssessment(Assessment assessment) {this.assessment = assessment;}
}
