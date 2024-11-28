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

    public static void executeCommand(Commande commande) {

    }

    public static void undo() {

    }
}
