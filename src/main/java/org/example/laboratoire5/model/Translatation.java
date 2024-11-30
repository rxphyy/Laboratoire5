package org.example.laboratoire5.model;

import org.example.laboratoire5.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Translatation extends Observable implements Perspective {
    private List<View> observers;
    private double movementValue;

    public Translatation(double movementValue) {
        this.movementValue = movementValue;
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(View view) {
        this.observers.add(view);
    }

    public double getMovementValue() {
        return movementValue;
    }
}
