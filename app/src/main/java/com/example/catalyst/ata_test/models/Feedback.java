package com.example.catalyst.ata_test.models;

/**
 * Created by dsloane on 4/22/2016.
 */
public class Feedback {

    private String score;
    private String comment;
    private int question_id;
    private int reviewId;
    private int id;

    public Feedback(){}

    public Feedback(int questionId, String score, String comment) {
        this.question_id = questionId;
        this.score = score;
        this.comment = comment;
    }


    public String getScore() {return score;}
    public void setScore(String score) {this.score = score;}
    public String getComment() {return comment;}
    public void setComment(String comment) {this.comment = comment;}
    public int getQuestion_id() {return question_id;}
    public void setQuestion_id(int question_id) {this.question_id = question_id;}
    public int getReviewId() {return reviewId;}
    public void setReviewId(int reviewId) {this.reviewId = reviewId;}

}
