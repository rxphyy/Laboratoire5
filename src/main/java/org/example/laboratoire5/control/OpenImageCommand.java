package org.example.laboratoire5.control;

import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.view.Application;
import org.example.laboratoire5.view.View;

import java.util.List;

public class OpenImageCommand extends Commande {
    private GestionnaireChargementImage gestionnaireChargementImage;
    private List<View> views;

    public OpenImageCommand(List<View> views) {
        this.views = views;
        this.gestionnaireChargementImage = new GestionnaireChargementImage();
    }

    @Override
    void executeCommand() {
        /*
        Image newImage = this.gestionnaireChargementImage.selectImageFromFileExplorer();
        if (newImage != null) {
            for (View view: this.views) {
                view.setImage(newImage);
                view.redraw();
            }
        } else {
            Application.Log.severe("Could not load the new image.");
        }

         */
        Image currentImage = !views.isEmpty() ? views.get(0).getImage() : null;
        if (currentImage != null) {
            gestionnaireChargementImage.addToImageHistory(currentImage);
        }

        Image newImage = gestionnaireChargementImage.selectImageFromFileExplorer();
        if (newImage != null) {
            for (View view : views) {
                view.setImage(newImage);
                view.redraw();
            }
        }
    }

    @Override
    void undoCommand() {
        Image previousImage = gestionnaireChargementImage.removeFromImageHistory();
        if (previousImage != null) {
            for (View view : views) {
                view.setImage(previousImage);
                view.redraw();
            }
        } else {
            Application.Log.warning("No previous image to undo.");
        }
    }
}
