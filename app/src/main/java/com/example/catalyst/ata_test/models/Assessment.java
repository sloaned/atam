package com.example.catalyst.ata_test.models;

import java.util.ArrayList;

/**
 * Created by dsloane on 4/22/2016.
 * This model represents the Assessment data model.
 */
public class Assessment {
    private int id;

    //Title of the assessment.
    private String name;

    //A list of questions
    private ArrayList<Question> questions;


    //Getters and Setter methods below.
    public int getId() {return id;}
    public void setId(int id) { this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public ArrayList<Question> getQuestions() {return questions;}
    public void setQuestions(ArrayList<Question> questions) {this.questions = questions;}
}
