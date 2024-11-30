package org.example.laboratoire5.view;

import org.example.laboratoire5.control.GestionnaireChargementImage;
import org.example.laboratoire5.control.GestionnaireCommandes;
import org.example.laboratoire5.control.ZoomCommand;

public class Controller {
    GestionnaireChargementImage gestionnaireChargementImage;
    GestionnaireCommandes gestionnaireCommandes;

    public Controller() {
        this.gestionnaireChargementImage = new GestionnaireChargementImage();
        this.gestionnaireCommandes = GestionnaireCommandes.getInstance();
    }

    public void executeZoomIn(View view, double zoomValue) {
        this.gestionnaireCommandes.executeCommand(new ZoomCommand(view, zoomValue));
    }

    public void executeZoomOut(View view, double zoomValue) {
        this.gestionnaireCommandes.executeCommand(new ZoomCommand(view, zoomValue));
    }

    public void openNewImage() {
        this.gestionnaireChargementImage.selectImageFromFileExplorer();
    }
}
