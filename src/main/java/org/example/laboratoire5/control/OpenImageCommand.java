package org.example.laboratoire5.control;

import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.view.Application;
import org.example.laboratoire5.view.View;

public class OpenImageCommand extends Commande {
    private GestionnaireChargementImage gestionnaireChargementImage;
    private View view;

    public OpenImageCommand(View view) {
        this.view = view;
        this.gestionnaireChargementImage = new GestionnaireChargementImage();
    }

    @Override
    void executeCommand() {
        Image newImage = this.gestionnaireChargementImage.selectImageFromFileExplorer();
        if (newImage != null) {
            view.setImage(newImage);
            view.redraw();
        } else {
            Application.Log.severe("Could not load the new image.");
        }
    }

    @Override
    void undoCommand() {

    }
}
