package org.example.laboratoire5.control;

import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Translation;
import org.example.laboratoire5.view.Application;
import org.example.laboratoire5.view.View;

public class TranslationCommand extends Commande {
    private View view;
    private Perspective translatePerspective;
    private double translateX;
    private double translateY;

    public TranslationCommand(View view, double translateX, double translateY) {
        this.view = view;
        this.translateX = translateX;
        this.translateY = translateY;
    }

    @Override
    void executeCommand() {
        translatePerspective = new Translation(translateX, translateY);
        translatePerspective.addObserver(this.view);
        this.view.addPerspective(translatePerspective);
    }

    @Override
    void undoCommand() {
        if (translatePerspective != null) {
            this.view.removePerspective(translatePerspective);
            this.view.redraw();
        } else {
            Application.Log.warning("No translate command to undo.");
        }
    }
}
