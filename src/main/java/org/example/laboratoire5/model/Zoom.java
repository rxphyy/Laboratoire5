package org.example.laboratoire5.model;

import org.example.laboratoire5.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Zoom extends Observable implements Perspective {
    private List<View> observers;
    private double scaleFactor;

    public Zoom(double scaleFactor) {
        this.observers = new ArrayList<>();
        this.scaleFactor = scaleFactor;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
        this.notifyObservers();
    }

    @Override
    public void addObserver(View view) {
        this.observers.add(view);
    }

    @Override
    public void removeObserver(View view) {
        this.observers.remove(view);
    }

    public void notifyObservers() {
        for (View observer : observers) {
            observer.update(this);
        }
    }
}
