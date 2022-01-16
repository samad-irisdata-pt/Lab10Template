package pt.pa.refactoring;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pt.pa.refactoring.model.Review;
import pt.pa.refactoring.model.Reviews;

import java.io.FileInputStream;

/**
 * @author amfs
 */
public class MainGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Reviews reviews = new Reviews();
        ListView listViewReviews = new ListView<>();

        BorderPane borderPane = new BorderPane();

        Scene scene = new Scene(borderPane,700,600);

        // Product grid pane
        GridPane productDescriptionGridPane = new GridPane();

        FileInputStream inputStream = new FileInputStream("resources/portatil.jpg");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);

        Label labelProductName = new Label("Product: ");
        labelProductName.setStyle("-fx-font-weight: bold");
        Label productName = new Label("Portátil XPTO");
        Label labelReleaseDate = new Label("Launch date: ");
        labelReleaseDate.setStyle("-fx-font-weight: bold");
        Label releaseDate = new Label("Janeiro de 2021");
        Label labelCPU = new Label("CPU: ");
        labelCPU.setStyle("-fx-font-weight: bold");
        Label cpu = new Label("Intel Core i7 @ 3.00GHz");
        Label labelRAM = new Label("RAM: ");
        labelRAM.setStyle("-fx-font-weight: bold");
        Label ram = new Label("16GB");
        Label labelStorage = new Label("Storage: ");
        labelStorage.setStyle("-fx-font-weight: bold");
        Label storage = new Label("1TB SSD NVMe M.2");
        Label labelWeight = new Label("Weight: ");
        labelWeight.setStyle("-fx-font-weight: bold");
        Label weight = new Label("1.3kg");
        Label labelBattery = new Label("Battery: ");
        labelBattery.setStyle("-fx-font-weight: bold");
        Label battery = new Label("65Wh");

        productDescriptionGridPane.add(labelProductName, 0, 0);
        productDescriptionGridPane.add(productName, 1,0);
        productDescriptionGridPane.add(labelReleaseDate, 0, 1);
        productDescriptionGridPane.add(releaseDate, 1,1);
        productDescriptionGridPane.add(labelCPU, 0, 2);
        productDescriptionGridPane.add(cpu, 1,2);
        productDescriptionGridPane.add(labelRAM, 0, 3);
        productDescriptionGridPane.add(ram, 1,3);
        productDescriptionGridPane.add(labelStorage, 0, 4);
        productDescriptionGridPane.add(storage, 1, 4);
        productDescriptionGridPane.add(labelWeight, 0,5);
        productDescriptionGridPane.add(weight, 1, 5);
        productDescriptionGridPane.add(labelBattery, 0,6);
        productDescriptionGridPane.add(battery, 1,6);

        // Review form
        GridPane gridPaneAddReview = new GridPane();

        Label labelAddReview = new Label("Add your review");
        labelAddReview.setStyle("-fx-font-weight: bold");
        gridPaneAddReview.add(labelAddReview, 0, 0);

        gridPaneAddReview.add(new Label("Your name"), 0, 1);
        TextField textFieldName = new TextField();
        gridPaneAddReview.add(textFieldName, 1, 1);

        gridPaneAddReview.add(new Label("Text"), 0, 2);
        TextField textFieldText = new TextField();
        gridPaneAddReview.add(textFieldText, 1, 2);

        gridPaneAddReview.add(new Label("Rating (0 to 5)"), 0, 3);
        TextField textFieldRating = new TextField();
        gridPaneAddReview.add(textFieldRating, 1, 3);
        Button buttonAddReview = new Button("Add");

        gridPaneAddReview.add(buttonAddReview, 1, 4);

        HBox hBoxProduct = new HBox(5);
        hBoxProduct.getChildren().add(imageView);
        hBoxProduct.getChildren().add(productDescriptionGridPane);
        hBoxProduct.getChildren().add(gridPaneAddReview);

        borderPane.setTop(hBoxProduct);

        // Reviews list
        GridPane gridPaneReviews = new GridPane();
        Label labelReviewsList = new Label(String.format("Reviews list (%d)", reviews.getTotal()));
        labelReviewsList.setStyle("-fx-font-weight: bold");

        gridPaneReviews.add(labelReviewsList, 0, 0);
        gridPaneReviews.add(listViewReviews, 0, 1);
        listViewReviews.setMinWidth(scene.getWidth());

        borderPane.setCenter(gridPaneReviews);

        buttonAddReview.setOnAction((ActionEvent e) -> {
            if (textFieldName.getText().isEmpty() || textFieldRating.getText().isEmpty() || textFieldText.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Review Error");
                alert.setHeaderText(null);
                alert.setContentText("Empty fields");
                alert.showAndWait();
            } else
            {
                try {
                    String name = textFieldName.getText();
                    String text = textFieldText.getText();
                    double rating = Double.parseDouble(textFieldRating.getText());

                    if (rating < 0 || rating > 5) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Review Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid rating");
                        alert.showAndWait();
                        return;
                    }

                    reviews.add(new Review(name, text, rating));
                    listViewReviews.getItems().clear();

                    for (Review r : reviews) {
                        listViewReviews.getItems().add(r);
                    }
                    textFieldName.clear();
                    textFieldText.clear();
                    textFieldRating.clear();
                    labelReviewsList.setText(String.format("Reviews list (%d) . Average rating (%.1f)", reviews.getTotal(), reviews.getAvgRating()));
                } catch (NumberFormatException nfe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Review Error");
                    alert.setHeaderText(null);
                    alert.setContentText("invalid format");
                    alert.showAndWait();
                }
            }
        });

        stage.setTitle("Product review");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

