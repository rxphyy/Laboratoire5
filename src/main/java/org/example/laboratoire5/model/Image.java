package org.example.laboratoire5.model;

import java.util.Observable;

public class Image extends Observable {
    private String sourcePath;

    public Image(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
}
