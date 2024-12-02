package org.example.laboratoire5.model;

import org.example.laboratoire5.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Translation extends Observable implements Perspective {
    private List<View> observers;
    private double translateX;
    private double translateY;

    public Translation(double translateX, double translateY) {
        this.observers = new ArrayList<>();
        this.translateY = translateY;
        this.translateX = translateX;
    }

    @Override
    public void addObserver(View view) {
        this.observers.add(view);
    }

    @Override
    public void removeObserver(View view) {
        this.observers.remove(view);
    }

    public double getTranslateX() {
        return translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public void setTranslationValues(double translateX, double translateY) {
        this.translateX = translateX;
        this.translateY = translateY;
        setChanged();
        notifyObservers();
    }

    public void notifyObservers() {
        for (View observer : observers) {
            observer.update(this);
        }
    }
}
