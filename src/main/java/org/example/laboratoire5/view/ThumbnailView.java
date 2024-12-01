package org.example.laboratoire5.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;

import java.util.Objects;

public class ThumbnailView extends View {
    private final ImageView imageView;

    public ThumbnailView(Image image) {
        super(image);

        imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        StackPane container = new StackPane(imageView);

        container.setMaxWidth(imageView.getFitWidth());
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
    public void redraw() {
        // TODO: Juste redraw l'image.
    }

    @Override
    public void update(Perspective perspective) {
        System.out.println("idk");
    }

    public void updateImage(String sourcePath) {
        try {
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + sourcePath)));
            imageView.setImage(fxImage);
            redraw();
        } catch (Exception e) {
            Application.Log.severe("Error loading image: '" + sourcePath + "'.");
        }
    }
}