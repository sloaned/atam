package com.example.catalyst.ata_test.events;

import android.util.Log;

/**
 * Created by dsloane on 5/9/2016.
 */
/* Fires when a user edits their user bio and hits "Save" */
public class BioChangeEvent {

    private String bio;

    public BioChangeEvent(String bio) {this.bio = bio;}

    public String getBio() {return bio;}
}
