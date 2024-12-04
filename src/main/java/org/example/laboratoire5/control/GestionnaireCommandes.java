package org.example.laboratoire5.control;

import org.example.laboratoire5.view.Application;

import java.util.Stack;

public class GestionnaireCommandes {
    private static GestionnaireCommandes gestionnaireCommandes = null;
    private Stack<Commande> commandes;
    private Stack<Commande> redo;

    private GestionnaireCommandes() {
        commandes = new Stack<>();
        redo = new Stack<>();
    }

    public static synchronized GestionnaireCommandes getInstance() {
        if (gestionnaireCommandes == null)
            gestionnaireCommandes = new GestionnaireCommandes();

        return gestionnaireCommandes;
    }

    public void executeCommand(Commande commande) {
        commande.executeCommand();
        this.addCommande(commande);
        this.redo.clear();
    }

    public void undo() {
        if (!commandes.isEmpty()) {
            Commande lastCommand = commandes.pop();
            lastCommand.undoCommand();
            redo.push(lastCommand);
        } else {
            Application.Log.warning("No commands to undo.");
        }
    }

    public void redo() {
        if (!redo.isEmpty()) {
            Commande commandToRedo = redo.pop();
            commandToRedo.executeCommand();
            commandes.push(commandToRedo);
        } else {
            Application.Log.warning("No commands to redo.");
        }
    }

    private void addCommande(Commande commande) {
        this.commandes.add(commande);
    }
}
