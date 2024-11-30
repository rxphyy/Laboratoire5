package org.example.laboratoire5.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.laboratoire5.model.Image;

import java.util.Objects;

public class ViewMenuWrapper extends BorderPane {
    private Controller controller;
    private ThumbnailView thumbnailView;
    private PerspectiveView perspectiveView1;
    private PerspectiveView perspectiveView2;

    public ViewMenuWrapper() {
        this.controller = new Controller();
        VBox vBox = new VBox();
        MenuBar menuBar = new MenuBar();

        Menu menuFichier = new Menu("Fichier") {{
            getItems().addAll(
                    createMenuItem("Sauvegarder", "save"),
                    createMenuItem("Ouvrir image", "openImage", e -> controller.openNewImage())
            );
        }};

        Menu menuPerspective = new Menu("Perspective") {{
            getItems().addAll(
                    createMenuItem("Zoom in", "zoomIn", e -> controller.executeZoom(perspectiveView1, 1.5)),
                    createMenuItem("Zoom out", "zoomOut"),
                    createMenuItem("Translate left", "translateLeft"),
                    createMenuItem("Translate right", "translateRight"),
                    createMenuItem("Translate down", "translateDown"),
                    createMenuItem("Translate up", "translateUp")
            );
        }};

        menuBar.getMenus().addAll(menuFichier, menuPerspective);

        Image image = new Image("org/example/laboratoire5/image.jpg");

        this.thumbnailView = new ThumbnailView(image);

        FlowPane perspectiveViewsFlowPane = new FlowPane();
        perspectiveViewsFlowPane.setPadding(new Insets(10));
        perspectiveViewsFlowPane.setHgap(10);
        perspectiveViewsFlowPane.setVgap(10);

        this.perspectiveView1 = new PerspectiveView(image);
        this.perspectiveView2 = new PerspectiveView(image);
        perspectiveViewsFlowPane.getChildren().addAll(perspectiveView1, perspectiveView2);

        vBox.getChildren().addAll(menuBar, thumbnailView, perspectiveViewsFlowPane);

        this.setCenter(vBox);
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
}