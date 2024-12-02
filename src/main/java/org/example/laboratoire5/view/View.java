package org.example.laboratoire5.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;

import java.util.ArrayList;
import java.util.List;

public abstract class View extends StackPane {
    public static final double MIN_ZOOM = 0.1;
    public static final double MAX_ZOOM = 5.0;

    private ImageView imageView;
    private Image image;
    private List<Perspective> perspectives;

    public View(Image image) {
        this.image = image;
        this.perspectives = new ArrayList<>();

        this.imageView = new ImageView();
        imageView.setPreserveRatio(true);

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
