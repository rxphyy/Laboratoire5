package org.example.laboratoire5.model;

import java.util.Observable;

public class Zoom extends Observable implements Perspective {
    private double scaleFactor = 1.0;

    public Zoom() {
        this.scaleFactor = 1.2;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void applyZoom(Image image) {
        System.out.println("Zoom applied");
    }
}
