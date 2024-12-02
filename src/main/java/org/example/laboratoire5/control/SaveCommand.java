package org.example.laboratoire5.control;

import org.example.laboratoire5.view.View;

public class SaveCommand extends Commande {
    private GestionnaireSauvegarde gestionnaireSauvegarde;
    private View view;

    public SaveCommand(View view) {
        this.gestionnaireSauvegarde = new GestionnaireSauvegarde();
    }

    @Override
    void executeCommand() {
        gestionnaireSauvegarde.saveState(view);
    }

    @Override
    void undoCommand() {
        gestionnaireSauvegarde.undo(view);
    }
}
