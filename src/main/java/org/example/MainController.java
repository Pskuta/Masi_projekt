package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    // FXML komponenty
    @FXML private ListView<String> listViewUnitermy;
    @FXML private Canvas centerCanvas;
    @FXML private TextField nameTextField;
    @FXML private TextField descTextField;
    @FXML private Button btnSekwencja;
    @FXML private Button btnZrownoleglenie;
    @FXML private Button btnMerge;
    @FXML private Button btnWyczysc;
    @FXML private Button btnDodaj;

    // Dane aplikacji
    private UnitermSekwencja sekwencjaUniterm;
    private UnitermZrownoleglenie zrownolegleniaUniterm;
    private Integer lastMergedChoice = null;
    private GraphicsContext gc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = centerCanvas.getGraphicsContext2D();

        // Ustawienie listenera dla ListView
        listViewUnitermy.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        wczytajZListy(newValue);
                    }
                }
        );

        // Podwójne kliknięcie na liście - pokazanie opisu
        listViewUnitermy.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedItem = listViewUnitermy.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    pokazOpis(selectedItem);
                }
            }
        });

        // Przypisanie akcji do przycisków
        btnSekwencja.setOnAction(e -> openSekwencjaWindow());
        btnZrownoleglenie.setOnAction(e -> openZrownolegleniaWindow());
        btnMerge.setOnAction(e -> openMergeWindow());
        btnWyczysc.setOnAction(e -> clearAll());
        btnDodaj.setOnAction(e -> dodajDoListy());

        // Odświeżenie listy przy starcie
        odswiezListe();
    }


    private void openSekwencjaWindow() {
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Dodaj Sekwencję");
            stage.setResizable(false);

            // Tworzenie layoutu
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20));

            // Komponenty
            TextField entryA = new TextField();
            TextField entryB = new TextField();
            ToggleGroup separatorGroup = new ToggleGroup();
            RadioButton radioSemicolon = new RadioButton(";");
            RadioButton radioComma = new RadioButton(",");

            radioSemicolon.setToggleGroup(separatorGroup);
            radioComma.setToggleGroup(separatorGroup);
            radioSemicolon.setSelected(true);

            Button btnDodaj = new Button("Dodaj");

            // Dodanie do layoutu
            grid.add(new Label("Wyrażenie A:"), 0, 0);
            grid.add(entryA, 1, 0);
            grid.add(new Label("Wyrażenie B:"), 0, 1);
            grid.add(entryB, 1, 1);
            grid.add(new Label("Operacja:"), 0, 2);

            HBox radioBox = new HBox(10);
            radioBox.getChildren().addAll(radioSemicolon, radioComma);
            grid.add(radioBox, 1, 2);
            grid.add(btnDodaj, 1, 3);

            // Akcja przycisku
            btnDodaj.setOnAction(e -> {
                String a = entryA.getText();
                String b = entryB.getText();
                String sep = ((RadioButton) separatorGroup.getSelectedToggle()).getText();
                String tekst = a + " " + sep + " " + b;

                // Usunięcie poprzedniej sekwencji
                clearCanvas("Uniterm1");

                // Tworzenie nowej sekwencji
                Font czcionka = Font.font("Arial", 16);
                sekwencjaUniterm = new UnitermSekwencja(30, 50, czcionka, tekst, 10);
                sekwencjaUniterm.draw(gc, 0, 0, "Uniterm1");

                stage.close();
            });

            Scene scene = new Scene(grid, 300, 200);
            stage.setScene(scene);

            // Centrowanie okna
            Stage primaryStage = (Stage) centerCanvas.getScene().getWindow();
            stage.setX(primaryStage.getX() + (primaryStage.getWidth() - 300) / 2);
            stage.setY(primaryStage.getY() + (primaryStage.getHeight() - 200) / 2);

            stage.showAndWait();

        } catch (Exception e) {
            showError("Błąd", "Nie można otworzyć okna sekwencji: " + e.getMessage());
        }
    }

    private void openZrownolegleniaWindow() {
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Dodaj Zrównoleglenie");
            stage.setResizable(false);

            // Tworzenie layoutu
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20));

            // Komponenty
            TextField entryA = new TextField();
            TextField entryB = new TextField();
            TextField entryC = new TextField();
            ToggleGroup separatorGroup = new ToggleGroup();
            RadioButton radioSemicolon = new RadioButton(";");
            RadioButton radioComma = new RadioButton(",");

            radioSemicolon.setToggleGroup(separatorGroup);
            radioComma.setToggleGroup(separatorGroup);
            radioSemicolon.setSelected(true);

            Button btnDodaj = new Button("Dodaj");

            // Dodanie do layoutu
            grid.add(new Label("Wyrażenie A:"), 0, 0);
            grid.add(entryA, 1, 0);
            grid.add(new Label("Wyrażenie B:"), 0, 1);
            grid.add(entryB, 1, 1);
            grid.add(new Label("Wyrażenie C:"), 0, 2);
            grid.add(entryC, 1, 2);
            grid.add(new Label("Operacja:"), 0, 3);

            HBox radioBox = new HBox(10);
            radioBox.getChildren().addAll(radioSemicolon, radioComma);
            grid.add(radioBox, 1, 3);
            grid.add(btnDodaj, 1, 4);

            // Akcja przycisku
            btnDodaj.setOnAction(e -> {
                String a = entryA.getText();
                String b = entryB.getText();
                String c = entryC.getText();
                String sep = ((RadioButton) separatorGroup.getSelectedToggle()).getText();

                // Usunięcie poprzedniego zrównoleglenia
                clearCanvas("Uniterm2");

                // Tworzenie nowego zrównoleglenia
                Font czcionka = Font.font("Arial", 16);
                List<String> linie = Arrays.asList(a, sep, b, sep, c);
                zrownolegleniaUniterm = new UnitermZrownoleglenie(linie, 30, 50, czcionka, 5, 10, 5);
                zrownolegleniaUniterm.draw(gc, 0, 200, "Uniterm2");

                stage.close();
            });

            Scene scene = new Scene(grid, 300, 250);
            stage.setScene(scene);

            // Centrowanie okna
            Stage primaryStage = (Stage) centerCanvas.getScene().getWindow();
            stage.setX(primaryStage.getX() + (primaryStage.getWidth() - 300) / 2);
            stage.setY(primaryStage.getY() + (primaryStage.getHeight() - 250) / 2);

            stage.showAndWait();

        } catch (Exception e) {
            showError("Błąd", "Nie można otworzyć okna zrównoleglenia: " + e.getMessage());
        }
    }

    private void openMergeWindow() {
        if (sekwencjaUniterm == null || zrownolegleniaUniterm == null) {
            showError("Błąd", "Najpierw utwórz sekwencję i zrównoleglenie!");
            return;
        }

        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Merge Uniterm");
            stage.setResizable(false);

            // Tworzenie layoutu
            VBox vbox = new VBox(20);
            vbox.setPadding(new javafx.geometry.Insets(20));

            Label label = new Label("Wybierz opcję merge:");

            ToggleGroup mergeGroup = new ToggleGroup();
            RadioButton radioA = new RadioButton("A");
            RadioButton radioB = new RadioButton("B");
            RadioButton radioC = new RadioButton("C");

            radioA.setToggleGroup(mergeGroup);
            radioB.setToggleGroup(mergeGroup);
            radioC.setToggleGroup(mergeGroup);

            HBox radioBox = new HBox(10);
            radioBox.getChildren().addAll(radioA, radioB, radioC);

            Button btnZamien = new Button("Zamień");

            vbox.getChildren().addAll(label, radioBox, btnZamien);

            // Akcja przycisku
            btnZamien.setOnAction(e -> {
                RadioButton selected = (RadioButton) mergeGroup.getSelectedToggle();
                if (selected != null) {
                    int choice = 0;
                    if (selected == radioA) choice = 1;
                    else if (selected == radioB) choice = 2;
                    else if (selected == radioC) choice = 3;

                    merge(choice);
                    lastMergedChoice = choice;
                    stage.close();
                }
            });

            Scene scene = new Scene(vbox, 300, 200);
            stage.setScene(scene);

            // Centrowanie okna
            Stage primaryStage = (Stage) centerCanvas.getScene().getWindow();
            stage.setX(primaryStage.getX() + (primaryStage.getWidth() - 300) / 2);
            stage.setY(primaryStage.getY() + (primaryStage.getHeight() - 200) / 2);

            stage.showAndWait();

        } catch (Exception e) {
            showError("Błąd", "Nie można otworzyć okna merge: " + e.getMessage());
        }
    }

    private void merge(int choice) {
        clearCanvas("Merged");
        SekZroMerge mergedUniterm = new SekZroMerge(zrownolegleniaUniterm, sekwencjaUniterm, choice);
        mergedUniterm.draw(gc, "Merged");
    }



    private void clearCanvas(String tag) {
        if ("all".equals(tag)) {
            gc.clearRect(0, 0, centerCanvas.getWidth(), centerCanvas.getHeight());
        } else {
            gc.clearRect(0, 0, centerCanvas.getWidth(), centerCanvas.getHeight());

            if (sekwencjaUniterm != null) {
                sekwencjaUniterm.draw(gc, 0, 0, "Uniterm1");
            }
            if (zrownolegleniaUniterm != null) {
                zrownolegleniaUniterm.draw(gc, 0, 200, "Uniterm2");
            }

        }
    }

    private void clearAll() {
        gc.clearRect(0, 0, centerCanvas.getWidth(), centerCanvas.getHeight());
        sekwencjaUniterm = null;
        zrownolegleniaUniterm = null;
        lastMergedChoice = null;
    }

    private void dodajDoListy() {
        String nazwa = nameTextField.getText().trim();
        String opis = descTextField.getText().trim();

        if (lastMergedChoice == null) {
            showWarning("UWAGA!", "Nie wybrano opcji Merge! Przed zapisaniem proszę wybrać opcję merge!");
            return;
        }

        if (nazwa.isEmpty()) {
            showWarning("Błąd", "Nazwa nie może być pusta!");
            return;
        }

        if (sekwencjaUniterm == null || zrownolegleniaUniterm == null) {
            showWarning("Błąd", "Najpierw utwórz sekwencję i zrównoleglenie!");
            return;
        }

        try {
            // Zapis do DataManager
            DataManager.saveUniterm(nazwa, opis, zrownolegleniaUniterm, sekwencjaUniterm, lastMergedChoice);

            // Odświeżenie listy
            odswiezListe();

            // Wyczyszczenie pól
            lastMergedChoice = null;
            nameTextField.clear();
            descTextField.clear();

            showInfo("Sukces", "Uniterm został dodany do listy!");

        } catch (Exception e) {
            showError("Błąd", "Nie można dodać do listy: " + e.getMessage());
        }
    }

    private void odswiezListe() {
        listViewUnitermy.getItems().clear();
        listViewUnitermy.getItems().addAll(DataManager.getAllUnitermNames());
    }

    private void wczytajZListy(String nazwa) {
        clearAll();

        try {
            DataManager.UnitermData data = DataManager.loadUniterm(nazwa);

            if (data != null) {
                // Tworzenie obiektów z danych
                zrownolegleniaUniterm = UnitermZrownoleglenie.fromMap(data.zrownolegleniaData);
                sekwencjaUniterm = UnitermSekwencja.fromMap(data.sekwencjaData);

                // Rysowanie na canvas
                zrownolegleniaUniterm.draw(gc, 0, 200, "Uniterm2");
                sekwencjaUniterm.draw(gc, 0, 0, "Uniterm1");

                lastMergedChoice = data.mergedChoice;
                merge(data.mergedChoice);
            }

        } catch (Exception e) {
            showError("Błąd", "Nie można wczytać danych: " + e.getMessage());
        }
    }

    private void pokazOpis(String nazwa) {
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Opis: " + nazwa);
            stage.setResizable(false);

            VBox vbox = new VBox(10);
            vbox.setPadding(new javafx.geometry.Insets(20));

            String opis = DataManager.getUnitermDescription(nazwa);
            if (opis == null || opis.trim().isEmpty()) {
                opis = "Brak opisu";
            }

            Label opisLabel = new Label(opis);
            opisLabel.setWrapText(true);
            opisLabel.setMaxWidth(280);

            Button btnUsun = new Button("Usuń");
            btnUsun.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
            btnUsun.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText("Czy na pewno chcesz usunąć uniterm '" + nazwa + "'?");
                alert.setContentText("Ta operacja jest nieodwracalna.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    DataManager.deleteUniterm(nazwa);
                    stage.close();
                    odswiezListe();
                    clearAll();
                }
            });

            vbox.getChildren().addAll(opisLabel, btnUsun);

            Scene scene = new Scene(vbox, 300, 150);
            stage.setScene(scene);

            // Centrowanie okna
            Stage primaryStage = (Stage) centerCanvas.getScene().getWindow();
            stage.setX(primaryStage.getX() + (primaryStage.getWidth() - 300) / 2);
            stage.setY(primaryStage.getY() + (primaryStage.getHeight() - 150) / 2);

            stage.showAndWait();

        } catch (Exception e) {
            showError("Błąd", "Nie można pokazać opisu: " + e.getMessage());
        }
    }

    // Metody pomocnicze do wyświetlania komunikatów
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


