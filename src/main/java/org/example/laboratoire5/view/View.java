package org.example.laboratoire5.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Zoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

abstract class View extends StackPane implements Observer {
    private Image image;
    private List<Perspective> perspectives;

    public View(Image image) {
        this.image = image;
        this.perspectives = new ArrayList<>();
        image.addObserver(this);
    }

    public abstract void update(Observable o, Object arg);

    private void redraw() {
        this.getChildren().clear();

        for (Perspective perspective : perspectives) {
            if (perspective instanceof Zoom) {
                this.setScaleX(((Zoom) perspective).getScaleFactor());
                this.setScaleY(((Zoom) perspective).getScaleFactor());
            }
        }

        ImageView imageView = new ImageView(image.getSourcePath());
        this.getChildren().add(imageView);
    }
}
