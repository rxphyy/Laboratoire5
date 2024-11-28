package org.example.laboratoire5.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.laboratoire5.model.Image;

public class ViewMenuWrapper extends BorderPane {
    private View view;

    public ViewMenuWrapper() {
        VBox vBox = new VBox();
        MenuBar menuBar = new MenuBar();

        Menu menuFichier = new Menu("Fichier") {{
            getItems().addAll(
                    new MenuItem("Sauvegarder"),
                    new MenuItem("Ouvrir")
            );
        }};

        menuBar.getMenus().addAll(menuFichier);

        this.view = new ThumbnailView(new Image());
        vBox.getChildren().addAll(menuBar, view);

        this.setCenter(vBox);
    }
}