package com.example.catalyst.ata_test.models;

import java.io.Serializable;

/**
 * Created by dsloane on 4/22/2016.
 */
public class User implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String role;
    private String title;
    private String description;
    private String email;

    public User() {}

    public User(String id, String first, String last, String role, String desc) {
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.role = role;
        this.description = desc;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
}
