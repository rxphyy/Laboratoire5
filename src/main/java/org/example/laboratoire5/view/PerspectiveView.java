package org.example.laboratoire5.view;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Translation;
import org.example.laboratoire5.model.Zoom;

public class PerspectiveView extends View {
    private double currentScale = 1.0;
    private ImageView imageView;

    public PerspectiveView(Image image) {
        super(image);

        Group group = updateImage(image.getSourcePath());
        this.getChildren().add(group);
    }

    public void enableMouseDrag(Controller controller) {
        final double[] mouseAnchorX = new double[1];
        final double[] mouseAnchorY = new double[1];

        this.setOnMousePressed(event -> {
            mouseAnchorX[0] = event.getSceneX();
            mouseAnchorY[0] = event.getSceneY();
        });

        this.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseAnchorX[0];
            double deltaY = event.getSceneY() - mouseAnchorY[0];

            // Reset anchors for next drag calculation
            mouseAnchorX[0] = event.getSceneX();
            mouseAnchorY[0] = event.getSceneY();

            // Trigger translation command
            controller.executeTranslate(this, deltaX, deltaY);
        });
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
            applyTranslate((Translation) perspective);
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

    private void applyTranslate(Translation translation) {
        System.out.println("Before: X=" + imageView.getLayoutX() + ", Y=" + imageView.getLayoutY());

        imageView.setLayoutX(imageView.getLayoutX() - translation.getTranslateX());
        imageView.setLayoutY(imageView.getLayoutY() - translation.getTranslateY());

        System.out.println("After: X=" + imageView.getLayoutX() + ", Y=" + imageView.getLayoutY());
    }

    public void addPerspective(Perspective perspective) {
        super.getPerspectives().add(perspective);
        drawPerspective(perspective);
    }

    private Group updateImage(String sourcePath) {
        try {
            imageView = new ImageView();
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image(sourcePath);
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
