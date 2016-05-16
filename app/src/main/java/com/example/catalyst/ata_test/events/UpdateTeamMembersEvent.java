package com.example.catalyst.ata_test.events;

import android.util.Log;

import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;

/**
 * Created by dsloane on 5/10/2016.
 */
/* fires when usernames of team members are pulled from database to update team page */
public class UpdateTeamMembersEvent {

    private final String TAG = UpdateTeamMembersEvent.class.getSimpleName();

    private ArrayList<User> memberList;

    public UpdateTeamMembersEvent(ArrayList<User> memberList) {this.memberList = memberList;
        Log.d(TAG, "in updateTeamMembersEvent constructor!!!!!!!!");
    }

    public ArrayList<User> getMemberList() {return memberList;}
}
