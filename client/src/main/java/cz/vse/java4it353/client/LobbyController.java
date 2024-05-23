package cz.vse.java4it353.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LobbyController {

    private static final Logger logger = LoggerFactory.getLogger(LobbyController.class);
    private static LobbyController instance;
    private Client client;

    @FXML
    private ListView<String> playersListView;
    @FXML
    private ListView<String> lobbiesListView;
    @FXML
    public TextField lobbyNameInput;
    @FXML
    public Button joinLobbyButton;
    public LobbyController() {
        instance = this;
    }
    public static LobbyController getInstance() {
        return instance;
    }

    public void initialize() {
        // Initialize components if needed
        try {
            client = Client.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    @FXML
    private void createLobby() {
        try {
            String lobbyName = lobbyNameInput.getText();
            String response = client.send("C " + lobbyName);
            if (response != null) {
                // Odstranění znaku 'J'
                if (response.startsWith("J ")) {
                    response = response.substring(2);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode lobbyNode = objectMapper.readTree(response);

                // Kontrola, zda JSON obsahuje klíč 'name'
                if (lobbyNode != null && lobbyNode.has("name")) {
                    String lobbyNameFromResponse = lobbyNode.get("name").asText();
                    lobbiesListView.getItems().add(lobbyNameFromResponse);
                } else {
                    logger.error("JSON response does not contain expected 'name' field: " + response);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to send create lobby command.", e);
        }
    }
    @FXML
    private void joinLobby() {
        try {
            String selectedLobby = lobbiesListView.getSelectionModel().getSelectedItem();
            if (selectedLobby != null && !selectedLobby.isEmpty()) {
                String response = client.send("J " + selectedLobby);
                if (response != null) {
                    // Odstranění znaku 'J'
                    if (response.startsWith("J ")) {
                        response = response.substring(2);
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode lobbyNode = objectMapper.readTree(response);

                    // Kontrola, zda JSON obsahuje klíč 'name'
                    if (lobbyNode != null && lobbyNode.has("name")) {

                        updatePlayersList(lobbyNode.get("players"));
                    } else {
                        logger.error("JSON response does not contain expected 'name' field: " + response);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to send join lobby command.", e);
        }
    }
    public void updatePlayersList(JsonNode players) {
        playersListView.getItems().clear();
        for (JsonNode player : players) {
            if (player != null && player.has("name")) {
                playersListView.getItems().add(player.get("name").asText());
            }
        }
    }
}