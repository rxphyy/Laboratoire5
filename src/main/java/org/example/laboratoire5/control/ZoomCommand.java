package org.example.laboratoire5.control;

import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Zoom;
import org.example.laboratoire5.view.Application;
import org.example.laboratoire5.view.View;

public class ZoomCommand extends Commande {
    private View view;
    private double zoomValue;
    private Perspective zoomPerspective;

    public ZoomCommand(View view, double zoomValue) {
        this.view = view;
        this.zoomValue = zoomValue;
    }

    @Override
    void executeCommand() {
        zoomPerspective = new Zoom(zoomValue);
        zoomPerspective.addObserver(view);
        view.addPerspective(zoomPerspective);
    }

    @Override
    void undoCommand() {
        if (zoomPerspective != null) {
            // Remove the last zoom perspective from the view
            view.removePerspective(zoomPerspective);
            view.redraw();  // Redraw the view after removing the perspective
        } else {
            Application.Log.warning("No zoom command to undo.");
        }
    }
}
