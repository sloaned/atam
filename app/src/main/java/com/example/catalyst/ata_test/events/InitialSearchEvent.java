package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;

/**
 * Created by dsloane on 5/6/2016.
 */
public class InitialSearchEvent {

    public ArrayList<User> userList;

    public InitialSearchEvent(ArrayList<User> userList) {
        this.userList = userList;
    }
}
