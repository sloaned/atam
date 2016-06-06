package com.example.catalyst.ata_test.models;

import java.util.Date;

/**
 * Created by dsloane on 5/10/2016.
 */
public class Kudo {
    private String reviewedId;
    private User reviewer;
    private String comment;
    private String submittedDate;
    //private Date submittedDate;

    public Kudo() {}

    public Kudo(String reviewedId, User reviewer, String comment, String submittedDate) {
        this.reviewedId = reviewedId;
        this.reviewer = reviewer;
        this.comment = comment;
        this.submittedDate = submittedDate;
    }

    public void setReviewed(String reviewedId) {this.reviewedId = reviewedId;}
    public String getReviewedId() {return reviewedId;}
    public void setReviewer(User reviewer) {this.reviewer = reviewer;}
    public User getReviewer() {return reviewer;}
    public void setKudo(String comment) {this.comment = comment;}
    public String getComment() {return comment;}
    public void setSubmittedDate(String date) {this.submittedDate = date;}
    public String getSubmittedDate() {return submittedDate;}
}
