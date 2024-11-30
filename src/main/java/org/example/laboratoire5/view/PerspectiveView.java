package org.example.laboratoire5.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Translatation;
import org.example.laboratoire5.model.Zoom;

import java.util.Observable;

public class PerspectiveView extends View {
    private ImageView imageView;
    private Scale scale;
    private Translate translate;

    public PerspectiveView(Image image) {
        super(image);
        // Initialize ImageView
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(500); // Default width for display
        imageView.setFitHeight(500); // Default height for display

        // Load the shared image
        updateImage(image.getSourcePath());

        // Add transforms
        scale = new Scale(1.0, 1.0); // No zoom initially
        translate = new Translate(0, 0); // No translation initially
        imageView.getTransforms().addAll(scale, translate);

        // Add ImageView to the StackPane
        this.getChildren().add(imageView);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void redraw() {
        for (Perspective perspective : super.getPerspectives()) {
            if (perspective instanceof Zoom) {
                System.out.println(((Zoom) perspective).getScaleFactor());
                this.setScaleX(((Zoom) perspective).getScaleFactor());
                this.setScaleY(((Zoom) perspective).getScaleFactor());
            }
        }
    }

    public void addPerspective(Perspective perspective) {
        super.getPerspectives().add(perspective);
        redraw();
    }

    private void updateImage(String sourcePath) {
        try {
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image(getClass().getResourceAsStream("/" + sourcePath));
            imageView.setImage(fxImage);
        } catch (Exception e) {
            System.err.println("Error loading image: " + sourcePath);
        }
    }

    private void setupZoomControls(Zoom zoom) {
        zoom.addObserver((observable, arg) -> {
            if (observable instanceof Zoom) {
                double zoomFactor = (double) arg; // Assuming zoom level is passed as argument
                scale.setX(zoomFactor);
                scale.setY(zoomFactor);
            }
        });
    }

    private void setupTranslationControls(Translatation translatation) {
        translatation.addObserver((observable, arg) -> {
            if (observable instanceof Translatation) {
                double[] translation = (double[]) arg; // Assuming [dx, dy] is passed as argument
                translate.setX(translation[0]);
                translate.setY(translation[1]);
            }
        });
    }
}
