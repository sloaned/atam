package com.example.catalyst.ata_test.models;

/**
 * Created by dsloane on 4/22/2016.
 */
public class Question {
    private int id;
    private String type;
    private String label;

    public Question() {}

    public Question(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getType() {return type;}
    public void setType(String type){this.type = type;}
    public String getLabel() {return label;}
    public void setLabel(String label) {this.label = label;}
}
