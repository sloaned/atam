package com.example.catalyst.ata_test.models;

/**
 * Created by dsloane on 4/22/2016.
 */
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String role;
    private String description;

    public User() {}

    public User(int id, String first, String last, String role, String desc) {
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.role = role;
        this.description = desc;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
}
