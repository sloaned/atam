package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.Profile;

/**
 * Created by dsloane on 5/31/2016.
 */
public class ProfileEvent {

    public ProfileEvent(Profile profile) {this.profile = profile;}

    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}
