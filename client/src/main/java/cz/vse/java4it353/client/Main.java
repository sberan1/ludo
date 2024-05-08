package cz.vse.java4it353.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class Main extends Application {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Starting JavaFX application");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Načtení FXML souboru
        Parent root = FXMLLoader.load(getClass().getResource("/aplikace.fxml"));

        // Vytvoření scény
        Scene scene = new Scene(root, 703, 980);

        // Nastavení scény a zobrazení hlavního okna
        primaryStage.setTitle("JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
