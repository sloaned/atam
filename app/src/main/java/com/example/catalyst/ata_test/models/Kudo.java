package com.example.catalyst.ata_test.models;

import java.util.Date;

/**
 * Created by dsloane on 5/10/2016.
 * The kudo class is used to load kudos from the database into this object.
 */
public class Kudo {
<<<<<<< HEAD
    private String reviewedId;
    private User reviewer;
    private String comment;
    private Date submittedDate;

    public Kudo() {}

    public Kudo(String reviewedId, User reviewer, String comment, Date submittedDate) {
        this.reviewedId = reviewedId;
        this.reviewer = reviewer;
        this.comment = comment;
        this.submittedDate = submittedDate;
    }

=======

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
>>>>>>> 4b1dd3d5679fe04d4aefbcfaf9027c4bd4c20dc4
    public void setReviewed(String reviewedId) {this.reviewedId = reviewedId;}
    public String getReviewedId() {return reviewedId;}
    public void setReviewer(User reviewer) {this.reviewer = reviewer;}
    public User getReviewer() {return reviewer;}
    public void setKudo(String comment) {this.comment = comment;}
    public String getComment() {return comment;}
<<<<<<< HEAD
    public void setSubmittedDate(Date date) {this.submittedDate = date;}
    public Date getSubmittedDate() {return submittedDate;}
=======
    public void setSubmittedDate(String date) {this.submittedDate = date;}
    public String getSubmittedDate() {return submittedDate;}
>>>>>>> 4b1dd3d5679fe04d4aefbcfaf9027c4bd4c20dc4
}
