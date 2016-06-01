package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.User;

/**
 * Created by dsloane on 5/31/2016.
 */
public class ProfileEvent {

    public ProfileEvent(User user) {this.user = user;}

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
