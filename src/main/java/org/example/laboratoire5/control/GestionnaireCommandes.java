package org.example.laboratoire5.control;

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

    }

    private void addCommande(Commande commande) {
        this.commandes.add(commande);
    }
}
