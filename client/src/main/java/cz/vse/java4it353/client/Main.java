package cz.vse.java4it353.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


public class Main extends Application {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static String PLAYER_NAME = "";

    public static void main(String[] args) {
        log.info("Starting JavaFX application");
        launch(args);
    }
    public static String getPlayerName() {
        return PLAYER_NAME;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        /*// Načtení FXML souboru
        Parent root = FXMLLoader.load(getClass().getResource("/aplikace.fxml"));

        // Vytvoření scény
        Scene scene = new Scene(root, 703, 980);

        // Nastavení scény a zobrazení hlavního okna
        primaryStage.setTitle("Člověče, nezlob se! - " + PLAYER_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
        log.info("Spuštěna aplikace z aplikace.fxml se jménem " + PLAYER_NAME);*/

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Login");
        dialog.setHeaderText("Zadejte své jméno");
        dialog.setContentText("Jméno:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            PLAYER_NAME = result.get();
            Parent root = FXMLLoader.load(getClass().getResource("/lobby.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Lobby pro Člověče, nezlob se! - " + PLAYER_NAME);
            primaryStage.setScene(scene);
            primaryStage.show();
            log.info("Spuštěna aplikace z lobby.fxml se jménem " + PLAYER_NAME);
        }
    }

    @Override
    public void stop() throws Exception {
        Client.getInstance().closeConnection();
        super.stop();
    }
}
