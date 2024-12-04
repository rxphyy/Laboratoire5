package org.example.laboratoire5.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.laboratoire5.control.Controller;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;
import org.example.laboratoire5.model.Translation;
import org.example.laboratoire5.model.Zoom;

public class PerspectiveView extends View {
    public PerspectiveView(Image image) {
        super(image);
        this.getChildren().add(updateImage(image.getSourcePath()));
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

            mouseAnchorX[0] = event.getSceneX();
            mouseAnchorY[0] = event.getSceneY();
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
        getImageView().setFitWidth(getImageView().getFitWidth() * zoom.getScaleFactor());
        getImageView().setFitHeight(getImageView().getFitHeight() * zoom.getScaleFactor());
    }


    private void applyTranslate(Translation translation) {
        getImageView().setLayoutX(getImageView().getLayoutX() + translation.getTranslateX());
        getImageView().setLayoutY(getImageView().getLayoutY() + translation.getTranslateY());
    }

    public void addPerspective(Perspective perspective) {
        super.getPerspectives().add(perspective);
        drawPerspective(perspective);
    }

    private ImageView updateImage(String sourcePath) {
        try {
            getImageView().setLayoutX(0);
            getImageView().setLayoutY(0);
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image(sourcePath);
            getImageView().setImage(fxImage);
            getImageView().setPreserveRatio(true);

            double imageWidth = fxImage.getWidth();
            double imageHeight = fxImage.getHeight();

            double scale = Math.min(800 / imageWidth, 500 / imageHeight);
            double scaledWidth = (imageWidth * scale);
            double scaledHeight = (imageHeight * scale);

            getImageView().setFitWidth(scaledWidth);
            getImageView().setFitHeight(scaledHeight);

            return getImageView();
        } catch (Exception e) {
            Application.Log.severe("Error loading image: " + sourcePath);
        }

        return null;
    }
}
