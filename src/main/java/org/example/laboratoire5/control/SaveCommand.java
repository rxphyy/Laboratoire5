package org.example.laboratoire5.control;

import org.example.laboratoire5.view.View;
import org.example.laboratoire5.view.ViewMenuWrapper;

public class SaveCommand extends Commande {
    private GestionnaireSauvegarde gestionnaireSauvegarde;
    private ViewMenuWrapper view;

    public SaveCommand(ViewMenuWrapper view) {
        this.view = view;
        this.gestionnaireSauvegarde = new GestionnaireSauvegarde();
    }

    @Override
    void executeCommand() {
        gestionnaireSauvegarde.saveState(view);
    }

    @Override
    void undoCommand() {
    }
}
