package com.example.catalyst.ata_test.models;

/**
 * Created by dsloane on 5/10/2016.
 */
public class Kudo {
    private User reviewed;
    private User reviewer;
    private String kudo;
    private String submittedDate;  // should probably be changed to a date

    public void setReviewed(User reviewed) {this.reviewed = reviewed;}
    public User getReviewed() {return reviewed;}
    public void setReviewer(User reviewer) {this.reviewer = reviewer;}
    public User getReviewer() {return reviewer;}
    public void setKudo(String kudo) {this.kudo = kudo;}
    public String getKudo() {return kudo;}
    public void setSubmittedDate(String date) {this.submittedDate = date;}
    public String getSubmittedDate() {return submittedDate;}
}
