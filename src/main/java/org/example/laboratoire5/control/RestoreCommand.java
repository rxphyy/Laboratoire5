package org.example.laboratoire5.control;

import org.example.laboratoire5.view.View;

public class RestoreCommand extends Commande {
    private GestionnaireSauvegarde gestionnaireSauvegarde;
    private View view;

    public RestoreCommand(View view) {
        this.gestionnaireSauvegarde = new GestionnaireSauvegarde();
        this.view = view;
    }

    @Override
    void executeCommand() {
        gestionnaireSauvegarde.loadState(view);
    }

    @Override
    void undoCommand() {
        gestionnaireSauvegarde.undo(view);
    }
}
