package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;

/**
 * Created by dsloane on 5/6/2016.
 * This class is used by the EventBus library.
 */
/* Fires when the search activity is launched */
public class InitialSearchEvent {

    public ArrayList<User> userList;

    public InitialSearchEvent(ArrayList<User> userList) {
        this.userList = userList;
    }
}
