package org.example.laboratoire5.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import org.example.laboratoire5.model.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ViewMenuWrapper extends BorderPane {
    private Controller controller;
    private ThumbnailView thumbnailView;

    private PerspectiveView perspectiveView1;
    private PerspectiveView perspectiveView2;

    private PerspectiveView selectedPerspectiveView;

    private List<View> views;

    public ViewMenuWrapper() {
        this.controller = new Controller();
        this.views = new ArrayList<>();

        VBox vBox = new VBox();
        MenuBar menuBar = new MenuBar();

        Menu menuFichier = new Menu("Fichier") {{
            getItems().addAll(
                    createMenuItem("Sauvegarder", "save", _ -> controller.executeSave(selectedPerspectiveView)),
                    createMenuItem("Ouvrir image", "openImage", _ -> controller.executeOpenImage(getViews())),
                    createMenuItem("Charger une sauvegarde", "restore", _ -> controller.executeRestore(selectedPerspectiveView))
            );
        }};

        Menu menuPerspective = new Menu("Perspective") {{
            getItems().addAll(
                    createMenuItem("Zoom in", "zoomIn", _ -> controller.executeZoom(selectedPerspectiveView, 1.5)),
                    createMenuItem("Zoom out", "zoomOut", _ -> controller.executeZoom(selectedPerspectiveView, 0.5)),
                    createMenuItem("Translate left", "translateLeft", _ -> controller.executeTranslate(selectedPerspectiveView,-5.0, 0.0)),
                    createMenuItem("Translate right", "translateRight", _ -> controller.executeTranslate(selectedPerspectiveView,5.0, 0.0)),
                    createMenuItem("Translate down", "translateDown", _ -> controller.executeTranslate(selectedPerspectiveView,0.0, 5.0)),
                    createMenuItem("Translate up", "translateUp", _ -> controller.executeTranslate(selectedPerspectiveView,0.0, -5.0))
            );
        }};

        menuBar.getMenus().addAll(menuFichier, menuPerspective);

        Image image = new Image("C:\\Users\\Raphael\\IdeaProjects\\Laboratoire5\\src\\main\\resources\\org\\example\\laboratoire5\\image.jpg");
        this.thumbnailView = new ThumbnailView(image);

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.5);

        HBox perspectiveViewHBox1 = new HBox();
        perspectiveViewHBox1.setAlignment(javafx.geometry.Pos.CENTER);
        perspectiveView1 = new PerspectiveView(image);
        //perspectiveView1.getImageView().setPreserveRatio(true);
        perspectiveViewHBox1.getChildren().add(perspectiveView1);

        HBox perspectiveViewHBox2 = new HBox();
        perspectiveViewHBox2.setAlignment(javafx.geometry.Pos.CENTER);
        perspectiveView2 = new PerspectiveView(image);
        //perspectiveView2.getImageView().setPreserveRatio(true);
        perspectiveViewHBox2.getChildren().add(perspectiveView2);

        splitPane.getItems().addAll(perspectiveViewHBox1, perspectiveViewHBox2);

        perspectiveView1.setOnScroll(this::handleScrollZoom);
        perspectiveView2.setOnScroll(this::handleScrollZoom);

        perspectiveView1.enableMouseDrag(controller);
        perspectiveView2.enableMouseDrag(controller);

        setClickHandler(perspectiveView1);
        setClickHandler(perspectiveView2);

        Button undoButton = createButton("Undo", "undo", _ -> controller.executeUndo());

        vBox.setSpacing(25);
        vBox.getChildren().addAll(menuBar, thumbnailView, splitPane, undoButton);

        this.setCenter(vBox);
        views.addAll(Arrays.asList(thumbnailView, perspectiveView1, perspectiveView2));

        this.setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.Z) {
                controller.executeUndo();
                e.consume();
            }
        });
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

    public List<View> getViews() {
        return views;
    }
}
