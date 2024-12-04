package org.example.laboratoire5.control;

import org.example.laboratoire5.view.View;
import org.example.laboratoire5.view.ViewMenuWrapper;

import java.io.Serializable;


public class Memento implements Serializable {
    private ViewMenuWrapper state;
    private static final long serialVersionUID = 1L;

    public Memento(ViewMenuWrapper state) {
        this.state = state;
    }

    public ViewMenuWrapper getState() {
        return state;
    }
}
