package cz.vse.java4it353.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LobbyController {

    private static final Logger logger = LoggerFactory.getLogger(LobbyController.class);
    private Client client;

    @FXML
    private ListView<String> playersListView;

    public void initialize() {
        // Initialize components if needed
        try {
            client = Client.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.send("C testovaci");
        //logger.info(p);
    }

    public void updateLobby(String response) {
        // Clear the list and update with new players
        playersListView.getItems().clear();
        String[] players = response.split(",");
        for (String player : players) {
            playersListView.getItems().add(player.trim());
        }
    }

    public void handleChooseColor() {
        String color = "RED"; // TESTOVACÍ BARVA
        try {
            Client.getInstance().send("CC " + color);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleStartGame() {
        try {
            Client.getInstance().send("S");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Close the current window and show the game window
        Stage stage = (Stage) playersListView.getScene().getWindow();
        stage.close();

        // Show aplikace.fxml
        try {
            FXMLLoader loader = FXMLLoader.load(getClass().getResource("/aplikace.fxml"));
            Stage gameStage = new Stage();
            gameStage.setScene(new Scene(loader.load()));
            gameStage.setTitle("Game");
            gameStage.show();
        } catch (IOException e) {
            logger.error("Error loading aplikace.fxml", e);
        }
    }
}