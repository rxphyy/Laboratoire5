package org.example.laboratoire5.control;

import org.example.laboratoire5.view.Application;

import java.util.Stack;

public class GestionnaireCommandes {
    private static GestionnaireCommandes gestionnaireCommandes = null;
    private Stack<Commande> commandes;

    private GestionnaireCommandes() {
        commandes = new Stack<>();
    }

    public static synchronized GestionnaireCommandes getInstance() {
        if (gestionnaireCommandes == null)
            gestionnaireCommandes = new GestionnaireCommandes();

        return gestionnaireCommandes;
    }

    public void executeCommand(Commande commande) {
        commande.executeCommand();
        this.addCommande(commande);
    }

    public void undo() {
        if (!commandes.isEmpty()) {
            Commande lastCommand = commandes.pop(); // Get the last command
            System.out.println(this.commandes.size());
            lastCommand.undoCommand(); // Undo it
        } else {
            Application.Log.warning("No commands to undo.");
        }
    }

    private void addCommande(Commande commande) {
        this.commandes.add(commande);
    }
}
