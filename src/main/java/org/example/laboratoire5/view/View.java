package org.example.laboratoire5.view;

import javafx.scene.layout.Pane;
import org.example.laboratoire5.model.Image;

import java.util.Observable;
import java.util.Observer;

abstract class View extends Pane implements Observer {
    private Image image;

    public View(Image image) {
        this.image = image;
        image.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
