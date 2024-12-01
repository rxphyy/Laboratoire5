package org.example.laboratoire5.control;

import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Translatation;
import org.example.laboratoire5.view.View;

public class TranslationCommand extends Commande {
    private View view;
    private double translate;
    private TranslateDirection direction;

    public TranslationCommand(View view, double translate, TranslateDirection direction) {
        this.view = view;
        this.translate = translate;
        this.direction = direction;
    }

    @Override
    void executeCommand() {
        Perspective perspective = new Translatation(translate);
        perspective.addObserver(view);
        view.addPerspective(perspective);
        ((Translatation) perspective).setMovementValue(translate);
    }

    @Override
    void undoCommand() {

    }
}
