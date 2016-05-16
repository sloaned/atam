package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.Kudo;

import java.util.ArrayList;

/**
 * Created by dsloane on 5/10/2016.
 */
/* on profile page load, gets kudos for a given user */
public class GetKudosInfoEvent {

    private ArrayList<Kudo> kudos;

    public GetKudosInfoEvent(ArrayList<Kudo> kudos) {this.kudos = kudos;}

    public ArrayList<Kudo> getKudos() {return kudos;}
}
