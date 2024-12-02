package org.example.laboratoire5.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.Perspective;

public class ThumbnailView extends View {
    public ThumbnailView(Image image) {
        super(image);

        getImageView().setFitWidth(100);
        getImageView().setFitHeight(100);

        StackPane container = new StackPane(getImageView());

        container.setMaxWidth(getImageView().getFitWidth());
        container.setBorder(new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                new CornerRadii(0),
                new BorderWidths(3)
        )));

        this.getChildren().add(container);
        updateImage(image.getSourcePath());
    }

    @Override
    public void redraw() {
        this.getChildren().clear();
        this.getChildren().add(updateImage(super.getImage().getSourcePath()));
    }

    @Override
    public void update(Perspective perspective) {
        System.out.println("idk");
    }

    public ImageView updateImage(String sourcePath) {
        try {
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image(sourcePath);
            getImageView().setImage(fxImage);
            return getImageView();
        } catch (Exception e) {
            Application.Log.severe("Error loading image: '" + sourcePath + "'.");
        }
        return null;
    }
}