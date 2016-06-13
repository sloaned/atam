package com.example.catalyst.ata_test.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by dsloane on 4/22/2016.
 *
 */
public class User implements Serializable, Parcelable {

    //Id of the user in the database
    private String id;

    //Variable for the first name.
    private String firstName;

    //Variable for the last name.
    private String lastName;

    //Professional title, i.e. Developer 1, Analyst 3
    private String title;

    //Users displayed Bio
    private String profileDescription;

    //Users Email
    private String email;

    //A url to the users avatar
    private String avatar;

    //Is this user currently working at the company
    private Boolean isActive;

    //The users hire date
    private String startDate;

    //The user last day working for catalyst.
    private String endDate;

    //Version load from the database.
    private Integer version;

    //Constructors.
    public User() {}
    public User(Parcel in) {
        id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        profileDescription = in.readString();
        title = in.readString();
       /* email = in.readString();
        avatar = in.readString();
        isActive = Boolean.valueOf(in.readString());
        String startDateString = in.readString();
        String endDateString = in.readString();
        version = in.readInt();
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            startDate = format.parse(startDateString);
            endDate = format.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }  */

    }
    public User(String id, String first, String last, String title, String desc, String email, String avatar,
                Boolean isActive, String startDate, String endDate, Integer version) {
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.title = title;
        this.profileDescription = desc;
        this.email = email;
        this.avatar = avatar;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
        this.version = version;
    }

    //Getters and setters
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getProfileDescription() {return profileDescription;}
    public void setProfileDescription(String profileDescription) {this.profileDescription = profileDescription;}
    public String getAvatar() {return avatar;}
    public void setAvatar(String avatar) {this.avatar = avatar;}
    public Boolean isActive() {return isActive;}
    public void setActive(boolean isActive) {this.isActive = isActive;}
    public String getStartDate() {return startDate;}
    public void setStartDate(String startDate) {this.startDate = startDate;}
    public String getEndDate() {return endDate;}
    public void setEndDate(String endDate) {this.endDate = endDate;}
    public Integer getVersion() {return version;}
    public void setVersion(Integer version) {this.version = version;}

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(firstName);
        out.writeString(lastName);
        out.writeString(profileDescription);
        out.writeString(title);
        out.writeString(email);
        out.writeString(avatar);
        out.writeString(String.valueOf(isActive));
        if (startDate != null && !startDate.equals(null)) {
            out.writeString(startDate.toString());
        }
        if (endDate != null && !endDate.equals(null)) {
            out.writeString(endDate.toString());
        }
        if (version != null) {
            out.writeInt(version);
        }
    }

    //A pointless Overide.
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
