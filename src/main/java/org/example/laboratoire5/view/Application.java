package org.example.laboratoire5.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Application extends javafx.application.Application {
    public static final Logger Log = Logger.getLogger(Application.class.getName());

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new ViewMenuWrapper()));
        stage.setTitle("Laboratoire 5 - Équipe C");
        stage.show();
    }

    public static void main(String[] args) {
        Logger rootLogger = Logger.getLogger(Application.class.getName());
        rootLogger.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new CustomFormatter());
        rootLogger.addHandler(handler);

        launch(args);
    }

    static class CustomFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            String levelColor;
            switch (record.getLevel().getName()) {
                case "SEVERE":
                    levelColor = "\u001B[31m"; // Red
                    break;
                case "WARNING":
                    levelColor = "\u001B[33m"; // Yellow
                    break;
                default:
                    levelColor = "\u001B[32m"; // Green
                    break;
            }

            String resetColor = "\u001B[0m";
            return String.format("%s%s: %s%s%n",
                    levelColor,
                    record.getLevel(),
                    record.getMessage(),
                    resetColor);
        }
    }
}
