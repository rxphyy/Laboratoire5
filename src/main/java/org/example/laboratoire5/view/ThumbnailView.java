package org.example.laboratoire5.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.laboratoire5.model.Image;

import java.util.Objects;
import java.util.Observable;

public class ThumbnailView extends View {
    private final ImageView imageView;

    public ThumbnailView(Image image) {
        super(image);

        imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);


        StackPane container = new StackPane(imageView);
        container.setBorder(new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                new CornerRadii(0),
                new BorderWidths(2)
        )));
        container.setStyle("-fx-background-color: white; -fx-padding: 3px");

        this.getChildren().add(container);
        updateImage(image.getSourcePath());
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private void updateImage(String sourcePath) {
        try {
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + sourcePath)));
            imageView.setImage(fxImage);
        } catch (Exception e) {
            System.err.println("Error loading image: '" + sourcePath + "'.");
        }
    }
}