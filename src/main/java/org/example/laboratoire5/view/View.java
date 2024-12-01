package org.example.laboratoire5.view;

import javafx.scene.layout.StackPane;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Zoom;

import java.util.ArrayList;
import java.util.List;

public abstract class View extends StackPane {
    public static final double MIN_ZOOM = 0.1;
    public static final double MAX_ZOOM = 5.0;

    private Image image;
    private List<Perspective> perspectives;

    public View(Image image) {
        this.image = image;
        this.perspectives = new ArrayList<>();
        image.addObserver(this);
    }

    public abstract void redraw();

    public void addPerspective(Perspective perspective) {
        this.perspectives.add(perspective);
        perspective.addObserver(this);
    }

    public void removePerspective(Perspective perspective) {
        perspective.removeObserver(this);
        this.perspectives.remove(perspective);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Perspective> getPerspectives() {
        return perspectives;
    }

    public abstract void update(Perspective perspective);
}
