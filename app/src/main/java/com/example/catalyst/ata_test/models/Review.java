package com.example.catalyst.ata_test.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dsloane on 4/22/2016.
 *
 */
public class Review {

    //Keep track of teams reviewer average.
    //Reviews are displayed to the user
    //as an average of all the reviews a
    //single team has given a user.
    private int teamId;

    //This is the object representing the individual leaving the review.
    private User reviewer;

    //This is the object representing the user being reviewed.
    private User reviewed;

    //Date the review was submitted.
    private Date submittedDate;

    //A list of feedback objects
    private ArrayList<Feedback> feedback;

    //Constructors
    public Review() {}
    public Review(User reviewer, User reviewed, Date submittedDate, int teamId, ArrayList<Feedback> fb) {
        this.reviewer = reviewer;
        this.reviewed = reviewed;
        this.submittedDate = submittedDate;
        this.teamId = teamId;
        feedback = fb;
    }

    //Getters and setters
    public User getReviewer() {return reviewer;}
    public void setReviewer(User reviewer) {this.reviewer = reviewer;}
    public User getReviewed() {return reviewed;}
    public void setReviewed(User reviewed) {this.reviewed = reviewed;}
    public Date getSubmittedDate() {return submittedDate;}
    public void setSubmittedDate(Date submittedDate) {this.submittedDate = submittedDate;}
    public ArrayList<Feedback> getFeedback() {return feedback;}
    public void setFeedback(ArrayList<Feedback> feedback) {this.feedback = feedback;}
    public int getTeamId() {return teamId;}
    public void setTeamId(int teamId) {this.teamId = teamId;}
}
