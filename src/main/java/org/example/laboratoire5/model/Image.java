package org.example.laboratoire5.model;

import org.example.laboratoire5.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Image extends Observable implements Serializable {
    private String sourcePath;
    private transient List<View> observers;

    public Image(String sourcePath) {
        this.sourcePath = sourcePath;
        this.observers = new ArrayList<>();
    }

    public void addObserver(View view) {
        this.observers.add(view);
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
}
