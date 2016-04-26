package com.example.catalyst.ata_test.models;

import java.util.ArrayList;

/**
 * Created by dsloane on 4/22/2016.
 */
public class Assessment {
    private int id;
    private String name;
    private ArrayList<Question> questions;

    public int getId() {return id;}
    public void setId(int id) { this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public ArrayList<Question> getQuestions() {return questions;}
    public void setQuestions(ArrayList<Question> questions) {this.questions = questions;}
}
