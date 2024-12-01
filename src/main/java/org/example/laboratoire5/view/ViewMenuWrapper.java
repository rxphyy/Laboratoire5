package org.example.laboratoire5.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import org.example.laboratoire5.control.TranslateDirection;
import org.example.laboratoire5.model.Image;

import java.util.Objects;

public class ViewMenuWrapper extends BorderPane {
    private Controller controller;
    private ThumbnailView thumbnailView;

    private PerspectiveView perspectiveView1;
    private PerspectiveView perspectiveView2;

    private PerspectiveView selectedPerspectiveView;

    public ViewMenuWrapper() {
        this.controller = new Controller();
        VBox vBox = new VBox();
        MenuBar menuBar = new MenuBar();

        Menu menuFichier = new Menu("Fichier") {{
            getItems().addAll(
                    createMenuItem("Sauvegarder", "save"),
                    createMenuItem("Ouvrir image", "openImage", e -> controller.executeOpenImage(perspectiveView1))
            );
        }};

        Menu menuPerspective = new Menu("Perspective") {{
            getItems().addAll(
                    createMenuItem("Zoom in", "zoomIn", _ -> controller.executeZoom(selectedPerspectiveView, 1.5)),
                    createMenuItem("Zoom out", "zoomOut", _ -> controller.executeZoom(selectedPerspectiveView, 0.5)),
                    createMenuItem("Translate left", "translateLeft", _ -> controller.executeTranslate(selectedPerspectiveView, 1.5, TranslateDirection.LEFT)),
                    createMenuItem("Translate right", "translateRight"),
                    createMenuItem("Translate down", "translateDown"),
                    createMenuItem("Translate up", "translateUp")
            );
        }};

        menuBar.getMenus().addAll(menuFichier, menuPerspective);

        Image image = new Image("org/example/laboratoire5/image.jpg");
        this.thumbnailView = new ThumbnailView(image);

        // Set up the SplitPane
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.5); // Split equally

        // HBox for perspectiveView1
        HBox perspectiveViewHBox1 = new HBox();
        perspectiveViewHBox1.setAlignment(javafx.geometry.Pos.CENTER);
        perspectiveView1 = new PerspectiveView(image);
        //perspectiveView1.getImageView().setPreserveRatio(true);
        perspectiveViewHBox1.getChildren().add(perspectiveView1);

        // HBox for perspectiveView2
        HBox perspectiveViewHBox2 = new HBox();
        perspectiveViewHBox2.setAlignment(javafx.geometry.Pos.CENTER);
        perspectiveView2 = new PerspectiveView(image);
        //perspectiveView2.getImageView().setPreserveRatio(true);
        perspectiveViewHBox2.getChildren().add(perspectiveView2);

        // Add HBoxes to SplitPane
        splitPane.getItems().addAll(perspectiveViewHBox1, perspectiveViewHBox2);

        // Set up zoom handling for both image views
        perspectiveView1.setOnScroll(this::handleScrollZoom);
        perspectiveView2.setOnScroll(this::handleScrollZoom);

        setClickHandler(perspectiveView1);
        setClickHandler(perspectiveView2);

        Button undoButton = createButton("Undo", "undo", _ -> controller.executeUndo());

        vBox.getChildren().addAll(menuBar, thumbnailView, splitPane, undoButton);

        this.setCenter(vBox);
    }

    private void adjustAspectRatio(PerspectiveView perspectiveView, double newWidth, Image image) {
        double aspectRatio = perspectiveView.getImageView().getImage().getHeight() / perspectiveView.getImageView().getImage().getWidth();
        double newHeight = newWidth * aspectRatio;
        perspectiveView.setPrefHeight(newHeight);
    }

    private void handleScrollZoom(ScrollEvent event) {
        double scaleFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
        this.controller.executeZoom((PerspectiveView) event.getSource(), scaleFactor);
    }

    private void setClickHandler(PerspectiveView perspectiveView) {
        perspectiveView.setOnMouseClicked(_ -> {
            if (selectedPerspectiveView == perspectiveView) {
                selectedPerspectiveView.setStyle("");
                selectedPerspectiveView = null;
            } else {
                if (selectedPerspectiveView != null) {
                    selectedPerspectiveView.setStyle("");
                }

                selectedPerspectiveView = perspectiveView;
                selectedPerspectiveView.setStyle("-fx-border-color: blue; -fx-border-width: 5px;"); // Highlight with a border
            }
        });
    }

    private MenuItem createMenuItem(String text, String icon, EventHandler<ActionEvent> onAction) {
        MenuItem menuItem = new MenuItem(text);

        ImageView menuIconView = new ImageView(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/laboratoire5/menuIcons/" + icon + ".png"))));
        menuIconView.setFitWidth(18);
        menuIconView.setFitHeight(18);

        menuItem.setGraphic(menuIconView);

        menuItem.setOnAction(onAction);

        return menuItem;
    }

    private MenuItem createMenuItem(String text, String icon) {
        return createMenuItem(text, icon, null);
    }

    private Button createButton(String text, String icon, EventHandler<ActionEvent> onAction) {
        Button button = new Button(text);

        if (icon != null && !icon.isEmpty()) {
            ImageView buttonIconView = new ImageView(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/laboratoire5/menuIcons/" + icon + ".png"))));
            buttonIconView.setFitWidth(18);
            buttonIconView.setFitHeight(18);
            button.setGraphic(buttonIconView);
        }

        button.setOnAction(onAction);

        return button;
    }
}
