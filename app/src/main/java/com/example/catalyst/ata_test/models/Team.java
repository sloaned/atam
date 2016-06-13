package com.example.catalyst.ata_test.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dsloane on 4/22/2016.
 */
public class Team implements Serializable, Parcelable {

    //ID of the team.
    private String id;

    //Title of the team
    private String name;

    //Records weather the team is active or not.
    private boolean active;

    //Holds the teams current assessment value.
    private Assessment assessment;

    //This lists holds all the users tht
    //are apart of this team.
    private ArrayList<User> memberList;

    //Team description.
    private String description;

    //Constructors
    public Team() {}
    public Team (String id, String name, boolean active, ArrayList<User> users, String desc) {
        this.id = id;
        this.name = name;
        this.active = active;
        memberList = users;
        description = desc;
    }

    //Getters and setters
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

    //Parcelable override.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeString(description);
    }

    //An unimportant ovveride. It was only overridden
    //to make the IDE/Compiler.
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public Team(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
    }

}
