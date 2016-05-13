package com.example.catalyst.ata_test.events;

import com.example.catalyst.ata_test.models.Kudo;

import java.util.ArrayList;

/**
 * Created by dsloane on 5/10/2016.
 */
public class UpdateKudosEvent {

    private ArrayList<Kudo> kudos;

    public UpdateKudosEvent(ArrayList<Kudo> kudos) {this.kudos = kudos;}

    public ArrayList<Kudo> getKudos() {return kudos;}
}
