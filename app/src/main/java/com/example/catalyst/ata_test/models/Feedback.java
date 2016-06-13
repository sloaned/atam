package com.example.catalyst.ata_test.models;

/**
 * Created by dsloane on 4/22/2016.
 * This represents a single feedback object
 * A Review is filled with feedback objects.
 */
public class Feedback {


    private int id;

    //TODO: Update this once the product owner confirms what the score numbers are going to be.
    //This holds a value from 1 to 5. Or 0 - 4, we are still waiting for the PO to confirm this.;=
    private String score;

    //A qualitative comment.
    private String comment;

    //This feedback object will be linked to a question in the database.
    private int question_id;

    //This is the ID of the individual who left the review.
    private int reviewId;

    //Contructors.
    public Feedback(){}
    public Feedback(int questionId, String score, String comment) {
        this.question_id = questionId;
        this.score = score;
        this.comment = comment;
    }

    //Getters and setters for class variables.
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
