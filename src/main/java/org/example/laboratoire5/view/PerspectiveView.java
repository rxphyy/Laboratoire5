package org.example.laboratoire5.view;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Translatation;
import org.example.laboratoire5.model.Zoom;

import java.util.List;
import java.util.Observable;

public class PerspectiveView extends View {
    private double currentScale = 1.0;
    private ImageView imageView;

    public PerspectiveView(Image image) {
        super(image);

        Group group = updateImage(image.getSourcePath());
        this.getChildren().add(group);
    }

    @Override
    public void redraw() {
        this.getChildren().clear();
        this.getChildren().add(updateImage(super.getImage().getSourcePath()));
        for (Perspective perspective : super.getPerspectives()) {
            drawPerspective(perspective);
        }
    }

    @Override
    public void update(Perspective perspective) {
        drawPerspective(perspective);
    }

    private void drawPerspective(Perspective perspective) {
        if (perspective instanceof Zoom)
            applyZoom((Zoom) perspective);
        else
            applyTranslate((Translatation) perspective);
    }

    private void applyZoom(Zoom zoom) {
        double newScale = currentScale * zoom.getScaleFactor();

        if (newScale >= MIN_ZOOM && newScale <= MAX_ZOOM) {
            imageView.setFitWidth(imageView.getFitWidth() * (newScale / currentScale));
            imageView.setFitHeight(imageView.getFitHeight() * (newScale / currentScale));
            currentScale = newScale;
        } else {
            Application.Log.info("Zoom limit reached.");
        }
    }

    private void applyTranslate(Translatation translatation) {
        System.out.println("Applied translate");
    }

    public void addPerspective(Perspective perspective) {
        super.getPerspectives().add(perspective);
        drawPerspective(perspective);
    }

    private Group updateImage(String sourcePath) {
        try {
            imageView = new ImageView();
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image(getClass().getResourceAsStream("/" + sourcePath));
            imageView.setImage(fxImage);
            imageView.setPreserveRatio(true);

            double imageWidth = fxImage.getWidth();
            double imageHeight = fxImage.getHeight();

            double scale = Math.min(500 / imageWidth, 500 / imageHeight);
            double scaledWidth = imageWidth * scale;
            double scaledHeight = imageHeight * scale;

            imageView.setFitWidth(scaledWidth);
            imageView.setFitHeight(scaledHeight);

            this.setMinWidth(scaledWidth);
            this.setMinHeight(scaledHeight);
            Group group = new Group(imageView);
            group.setClip(new Rectangle(scaledWidth, scaledHeight));

            imageView.setClip(new Rectangle(scaledWidth, scaledHeight));
            return group;
        } catch (Exception e) {
            Application.Log.severe("Error loading image: " + sourcePath);
        }

        return null;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
