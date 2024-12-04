package org.example.laboratoire5.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import org.example.laboratoire5.control.Controller;
import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.model.ViewMenuWrapperSerializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ViewMenuWrapper extends VBox implements Serializable {
    private transient Controller controller;
    private transient ThumbnailView thumbnailView;

    private transient PerspectiveView perspectiveView1;
    private transient PerspectiveView perspectiveView2;

    private transient PerspectiveView selectedPerspectiveView;

    private List<View> views;
    private Image image;

    public ViewMenuWrapper() {
        this.controller = new Controller();
        this.views = new ArrayList<>();

        MenuBar menuBar = new MenuBar();

        Menu menuFichier = new Menu("Fichier") {{
            getItems().addAll(
                    createMenuItem("Sauvegarder", "save", _ -> controller.executeSave(ViewMenuWrapper.this)),
                    createMenuItem("Ouvrir image", "openImage", _ -> controller.executeOpenImage(getViews()))
            );
        }};

        Menu menuPerspective = new Menu("Perspective") {{
            getItems().addAll(
                    createMenuItem("Zoom in", "zoomIn", _ -> controller.executeZoom(selectedPerspectiveView, 1.5)),
                    createMenuItem("Zoom out", "zoomOut", _ -> controller.executeZoom(selectedPerspectiveView, 0.5)),
                    new SeparatorMenuItem(),
                    createMenuItem("Translate", "translate", _ -> showTranslateDialog()),
                    new SeparatorMenuItem(),
                    createMenuItem("Translate left", "translateLeft", _ -> controller.executeTranslate(selectedPerspectiveView,-5.0, 0.0)),
                    createMenuItem("Translate right", "translateRight", _ -> controller.executeTranslate(selectedPerspectiveView,5.0, 0.0)),
                    createMenuItem("Translate down", "translateDown", _ -> controller.executeTranslate(selectedPerspectiveView,0.0, 5.0)),
                    createMenuItem("Translate up", "translateUp", _ -> controller.executeTranslate(selectedPerspectiveView,0.0, -5.0))
            );
        }};

        menuBar.getMenus().addAll(menuFichier, menuPerspective);

        this.image = new Image("file:./src/main/resources/org/example/laboratoire5/image.jpg");
        this.thumbnailView = new ThumbnailView(image);

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.5);
        this.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(splitPane, Priority.NEVER);

        //HBox perspectiveViewHBox1 = new HBox();
        //perspectiveViewHBox1.setAlignment(javafx.geometry.Pos.CENTER);
        perspectiveView1 = new PerspectiveView(image);
        //perspectiveView1.getImageView().setPreserveRatio(true);
        //perspectiveViewHBox1.getChildren().add(perspectiveView1);

        //HBox perspectiveViewHBox2 = new HBox();
        //perspectiveViewHBox2.setAlignment(javafx.geometry.Pos.CENTER);
        perspectiveView2 = new PerspectiveView(image);
        //perspectiveView2.getImageView().setPreserveRatio(true);
        //perspectiveViewHBox2.getChildren().add(perspectiveView2);

        perspectiveView1.setStyle("-fx-margin: 5px;");
        splitPane.setMaxHeight(perspectiveView1.getImageView().getFitHeight());
        splitPane.setMinHeight(perspectiveView1.getImageView().getFitHeight());
        splitPane.setStyle("-fx-margin: 5px;");
        splitPane.getItems().addAll(perspectiveView1, perspectiveView2);

        perspectiveView1.setOnScroll(this::handleScrollZoom);
        perspectiveView2.setOnScroll(this::handleScrollZoom);

        perspectiveView1.enableMouseDrag(controller);
        perspectiveView2.enableMouseDrag(controller);

        setClickHandler(perspectiveView1);
        setClickHandler(perspectiveView2);

        Button undoButton = createButton("Undo", "undo", _ -> controller.executeUndo());
        Button redoButton = createButton("Redo", "redo", _ -> controller.executeRedo());

        HBox buttons = new HBox();
        buttons.getChildren().addAll(undoButton, redoButton);
        buttons.setFillHeight(false);
        buttons.setAlignment(Pos.CENTER);

        this.setSpacing(25);
        this.getChildren().addAll(menuBar, thumbnailView, splitPane, buttons);

        views.addAll(Arrays.asList(thumbnailView, perspectiveView1, perspectiveView2));

        this.setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.Z) {
                controller.executeUndo();
                e.consume();
            }
        });

        this.setStyle("-fx-margin: 5px;");
    }

    private void showTranslateDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Dialogue translation");
        dialog.setHeaderText("Choisissez des valeurs de translations pour les axes x et y");

        VBox vBox = new VBox(10);

        Slider xSlider = new Slider(-100, 100, 0);  // Range from -100 to 100
        xSlider.setShowTickMarks(true);
        xSlider.setShowTickLabels(true);
        xSlider.setMajorTickUnit(20);
        xSlider.setBlockIncrement(5);

        Slider ySlider = new Slider(-100, 100, 0);  // Range from -100 to 100
        ySlider.setShowTickMarks(true);
        ySlider.setShowTickLabels(true);
        ySlider.setMajorTickUnit(20);
        ySlider.setBlockIncrement(5);

        TextField xValueField = new TextField(String.valueOf(xSlider.getValue()));
        TextField yValueField = new TextField(String.valueOf(ySlider.getValue()));

        xSlider.valueProperty().addListener((_, _, newValue) -> {
            xValueField.setText(String.format("%.2f", newValue.doubleValue()));
        });

        ySlider.valueProperty().addListener((_, _, newValue) -> {
            yValueField.setText(String.format("%.2f", newValue.doubleValue()));
        });

        Button xIncrementButton = new Button("↑");
        Button xDecrementButton = new Button("↓");
        Button yIncrementButton = new Button("↑");
        Button yDecrementButton = new Button("↓");

        xIncrementButton.setOnAction(_ -> xSlider.setValue(xSlider.getValue() + 1));
        xDecrementButton.setOnAction(_ -> xSlider.setValue(xSlider.getValue() - 1));
        yIncrementButton.setOnAction(_ -> ySlider.setValue(ySlider.getValue() + 1));
        yDecrementButton.setOnAction(_ -> ySlider.setValue(ySlider.getValue() - 1));

        ComboBox<String> viewSelectionComboBox = new ComboBox<>();
        viewSelectionComboBox.getItems().addAll("Gauche", "Droite");
        viewSelectionComboBox.setValue("Gauche");

        vBox.getChildren().addAll(
                new Label("Choisissez la vue:"),
                viewSelectionComboBox,
                new Label("Mouvement sur l'axe des x : "),
                xSlider,
                new HBox(5, xDecrementButton, xValueField, xIncrementButton), // Arrow buttons for X
                new Label("Mouvement sur l'axe des y : "),
                ySlider,
                new HBox(5, yDecrementButton, yValueField, yIncrementButton)  // Arrow buttons for Y
        );

        dialog.getDialogPane().setContent(vBox);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        dialog.setResultConverter(button -> {
            if (button == okButton) {
                double x = xSlider.getValue();
                double y = ySlider.getValue();

                PerspectiveView selectedView = viewSelectionComboBox.getValue().equals("Gauche")
                        ? perspectiveView1
                        : perspectiveView2;

                controller.executeTranslate(selectedView, x, y);
            }
            return null;
        });

        dialog.showAndWait();
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
                selectedPerspectiveView.setStyle("-fx-border-color: blue; -fx-border-style: solid inside; -fx-padding: 5px; -fx-margin: 5px; -fx-border-width: 5px;"); // Highlight with a border
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

    public Image getImage() {
        return image;
    }
}
