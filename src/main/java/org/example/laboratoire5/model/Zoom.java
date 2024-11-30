package org.example.laboratoire5.model;

import org.example.laboratoire5.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Zoom extends Observable implements Perspective {
    private List<View> observers;
    private double scaleFactor = 1.0;

    public Zoom() {
        this.observers = new ArrayList<>();
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
        setChanged();
        notifyObservers();
    }

    @Override
    public void addObserver(View view) {
        this.observers.add(view);
    }
}
