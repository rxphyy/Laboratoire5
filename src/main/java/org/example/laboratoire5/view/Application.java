package org.example.laboratoire5.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new ViewMenuWrapper(), 1030, 500));
        stage.setTitle("Laboratoire 5 - Équipe C");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
