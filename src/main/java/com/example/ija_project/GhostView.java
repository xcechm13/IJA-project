package com.example.ija_project;

import java.util.Observable;

public class GhostView extends Observable {

    private String zprava;

    public GhostView()
    {

    }

    public String getZprava()
    {
        return zprava;
    }

    public void setZprava(String zprava)
    {
        this.zprava = zprava;
        setChanged();
        notifyObservers(zprava);
    }
}
