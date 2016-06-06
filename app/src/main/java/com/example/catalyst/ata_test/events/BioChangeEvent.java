package com.example.catalyst.ata_test.events;

/**
 * Created by dsloane on 5/9/2016.
 * This class is used by the EventBus library.
 */
/* Fires when a user edits their user bio and hits "Save" */
public class BioChangeEvent {

    private String bio;

    public BioChangeEvent(String bio) {this.bio = bio;}

    public String getBio() {return bio;}
}
