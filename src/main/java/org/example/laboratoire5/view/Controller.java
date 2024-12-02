package org.example.laboratoire5.view;

import org.example.laboratoire5.control.*;

import java.util.List;

public class Controller {
    GestionnaireChargementImage gestionnaireChargementImage;
    GestionnaireCommandes gestionnaireCommandes;
    GestionnaireSauvegarde gestionnaireSauvegarde;

    public Controller() {
        this.gestionnaireChargementImage = new GestionnaireChargementImage();
        this.gestionnaireCommandes = GestionnaireCommandes.getInstance();
        this.gestionnaireSauvegarde = new GestionnaireSauvegarde();
    }

    public void executeZoom(View view, double zoomValue) {
        this.gestionnaireCommandes.executeCommand(new ZoomCommand(view, zoomValue));
    }

    public void executeTranslate(View view, double translateX, double translateY) {
        this.gestionnaireCommandes.executeCommand(new TranslationCommand(view, translateX, translateY));
    }

    public void executeOpenImage(List<View> views) {
        this.gestionnaireCommandes.executeCommand(new OpenImageCommand(views));
    }

    public void executeSave(View view) {
        this.gestionnaireCommandes.executeCommand(new SaveCommand(view));
    }

    public void executeRestore(View view) {
        this.gestionnaireCommandes.executeCommand(new RestoreCommand(view));
    }

    public void executeUndo() {
        this.gestionnaireCommandes.undo();
    }
}
