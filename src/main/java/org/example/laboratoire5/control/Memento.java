package org.example.laboratoire5.control;

import org.example.laboratoire5.view.View;

public class Memento {
    private View state;
    private static final long serialVersionUID = 1L;

    public Memento(View state) {
        this.state = state;
    }

    public View getState() {
        return state;
    }
}
