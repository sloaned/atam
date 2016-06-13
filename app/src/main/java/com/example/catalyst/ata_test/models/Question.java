package com.example.catalyst.ata_test.models;

/**
 * Created by dsloane on 4/22/2016.
 * An assessment is
 */
public class Question {

    //Database Id of the question
    private int id;

    //The type of questions this is.
    private String type;

    //TODO: Find out the importance of the label, and comment it appropriately.
    private String label;

    //Constructors
    public Question() {}
    public Question(int id, String label) {
        this.id = id;
        this.label = label;
    }

    //Getters and setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getType() {return type;}
    public void setType(String type){this.type = type;}
    public String getLabel() {return label;}
    public void setLabel(String label) {this.label = label;}
}
