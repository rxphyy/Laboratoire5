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
    private ImageView imageView;
    private Image image;

    public PerspectiveView(Image image) {
        super(image);
        this.image = image;

        Group group = updateImage(image.getSourcePath());

        this.getChildren().add(group);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("UPDATE!!!");
        redraw();
    }

    @Override
    public void redraw() {
        this.getChildren().clear();
        this.getChildren().add(updateImage(image.getSourcePath()));
        for (Perspective perspective : super.getPerspectives()) {
            if (perspective instanceof Zoom) {
                System.out.println(((Zoom) perspective).getScaleFactor());

                imageView.setFitWidth(imageView.getFitWidth() * ((Zoom) perspective).getScaleFactor());
                imageView.setFitHeight(imageView.getFitHeight() * ((Zoom) perspective).getScaleFactor());
            }
        }
    }

    public void addPerspective(Perspective perspective) {
        super.getPerspectives().add(perspective);
        redraw();
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
            Group group = new Group(imageView);
            group.setClip(new Rectangle(scaledWidth, scaledHeight));

            imageView.setClip(new Rectangle(scaledWidth, scaledHeight));
            return group;
        } catch (Exception e) {
            System.err.println("Error loading image: " + sourcePath);
        }

        return null;
    }
}
