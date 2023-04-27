package com.example.ija_project;

import java.util.Observable;
import java.util.Observer;

public class GhostObject implements Observer {

    private GhostView ghostView;

    public GhostObject(GhostView ghostView)
    {
        this.ghostView = ghostView;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Dokončeno: " + arg);
    }
}
