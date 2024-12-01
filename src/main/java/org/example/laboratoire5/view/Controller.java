package org.example.laboratoire5.view;

import org.example.laboratoire5.control.*;

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

    public void executeTranslate(View view, double translateValue, TranslateDirection direction) {
        this.gestionnaireCommandes.executeCommand(new TranslationCommand(view, translateValue, direction));
    }

    public void executeOpenImage(View view) {
        this.gestionnaireCommandes.executeCommand(new OpenImageCommand(view));
    }

    public void executeSave() {
        this.gestionnaireCommandes.executeCommand(new SaveCommand());
    }

    public void executeUndo() {
        this.gestionnaireCommandes.undo();
    }
}
