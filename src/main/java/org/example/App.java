package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Ładowanie FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);

            primaryStage.setTitle("Uniterm Manager");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Błąd podczas uruchamiania aplikacji: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}