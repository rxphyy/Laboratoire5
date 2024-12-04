package org.example.laboratoire5.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class View extends Pane implements Serializable {
    public static final double MIN_ZOOM = 0.1;
    public static final double MAX_ZOOM = 5.0;

    private transient ImageView imageView;
    private Image image;
    private List<Perspective> perspectives;

    public View(Image image) {
        this.image = image;
        this.perspectives = new ArrayList<>();

        this.imageView = new ImageView();
        this.imageView.setPreserveRatio(true);

        this.setStyle("-fx-border-color: red; -fx-border-width: 5; -fx-border-style: solid outside;");

        image.addObserver(this);
    }

    public abstract void redraw();
    public abstract void update(Perspective perspective);

    public void addPerspective(Perspective perspective) {
        this.perspectives.add(perspective);
        perspective.addObserver(this);
    }

    public void removePerspective(Perspective perspective) {
        perspective.removeObserver(this);
        this.perspectives.remove(perspective);
    }

    public List<Perspective> getPerspectives() {
        return perspectives;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
