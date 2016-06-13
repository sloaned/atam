package com.example.catalyst.ata_test.models;

/**
 * Created by dsloane on 5/10/2016.
 * The kudo class is used to load kudos from the database into this object.
 */
public class Kudo {

    //Reviewed ID is the ID of the individual whom
    //the kudo is being targeted at.
    private String reviewedId;

    //This is the reference to the user who leaving the kudo.
    private User reviewer;

    //The comment the user is leaving.
    private String comment;

    //This represents when the kudo was left
    private String submittedDate;

    //Constructors
    public Kudo() {}
    public Kudo(String reviewedId, User reviewer, String comment, String submittedDate) {
        this.reviewedId = reviewedId;
        this.reviewer = reviewer;
        this.comment = comment;
        this.submittedDate = submittedDate;
    }

    //Getters and Setters
    public void setReviewed(String reviewedId) {this.reviewedId = reviewedId;}
    public String getReviewedId() {return reviewedId;}
    public void setReviewer(User reviewer) {this.reviewer = reviewer;}
    public User getReviewer() {return reviewer;}
    public void setKudo(String comment) {this.comment = comment;}
    public String getComment() {return comment;}
    public void setSubmittedDate(String date) {this.submittedDate = date;}
    public String getSubmittedDate() {return submittedDate;}
}
