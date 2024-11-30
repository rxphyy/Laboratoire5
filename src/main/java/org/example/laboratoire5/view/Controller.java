package org.example.laboratoire5.view;

import org.example.laboratoire5.control.GestionnaireChargementImage;
import org.example.laboratoire5.model.Zoom;

public class Controller {
    GestionnaireChargementImage gestionnaireChargementImage;

    public Controller() {
        this.gestionnaireChargementImage = new GestionnaireChargementImage();
    }

    public void executeZoom(View view, double zoomValue) {
        Zoom zoom = new Zoom();
        view.addPerspective(zoom);
    }

    public void openNewImage() {
        this.gestionnaireChargementImage.selectImageFromFileExplorer();
    }
}
