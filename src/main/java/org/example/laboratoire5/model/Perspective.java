package org.example.laboratoire5.model;

import org.example.laboratoire5.view.View;

public interface Perspective {
    void addObserver(View view);
    void removeObserver(View view);
}
