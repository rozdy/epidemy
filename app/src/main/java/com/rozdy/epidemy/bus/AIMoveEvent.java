package com.rozdy.epidemy.bus;

/**
 * Created by Sergejj on 11/10/2015 in the name of the Emperor!
 */
public class AIMoveEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public AIMoveEvent(int position) {
        this.position = position;
    }
}
