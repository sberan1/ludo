package cz.vse.java4it353.client;

import cz.vse.java4it353.client.controllers.LobbyController;
import cz.vse.java4it353.client.model.Lobby;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * Třída main, ve které se spouští aplikace
 */
public class Main extends Application {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static String PLAYER_NAME = "";
    private static Lobby MAIN_LOBBY = new Lobby();

    /**
     * Main metoda, ve které se spouští aplikace
     * @param args
     */
    public static void main(String[] args) {
        log.info("Starting JavaFX application");
        launch(args);
    }
    public static String getPlayerName() {
        return PLAYER_NAME;
    }
    public static Lobby getLobby() { return MAIN_LOBBY; }
    public static void setLobby(Lobby lobby) {
        MAIN_LOBBY = lobby;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Login");
        dialog.setHeaderText("Zadejte své jméno");
        dialog.setContentText("Jméno:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            PLAYER_NAME = result.get();
            Parent root = FXMLLoader.load(getClass().getResource("/lobby.fxml"));
            LobbyController.primaryStage = primaryStage;
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
        Client.getInstance().stop();
        super.stop();
    }
}
