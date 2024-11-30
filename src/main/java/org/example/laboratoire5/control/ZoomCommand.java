package org.example.laboratoire5.control;

import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Zoom;
import org.example.laboratoire5.view.View;

public class ZoomCommand extends Commande {
    private View view;
    private double zoomValue;

    public ZoomCommand(View view, double zoomValue) {
        this.view = view;
        this.zoomValue = zoomValue;
    }

    @Override
    void executeCommand() {
        Perspective perspective = new Zoom();
        perspective.addObserver(view);
        view.addPerspective(perspective);
        ((Zoom) perspective).setScaleFactor(zoomValue);
    }
}
