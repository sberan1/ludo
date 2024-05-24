package cz.vse.java4it353.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.*;

public class LobbyController {

    private static final Logger logger = LoggerFactory.getLogger(LobbyController.class);
    private Map<String, List<String>> lobbyPlayersMap = new HashMap<>();
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
            String command = "L " + Main.playerName;
            String response = client.send(command);
            if(response != null) {
                if (response.startsWith("L ")) {
                    response = response.substring(2);
                    logger.info("ODSTRANĚNÍ ZNAKU L");
                }
                processLobbies(response);
            }
            lobbiesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    updatePlayersListView(newValue);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateLobbiesListView() {
        ObservableList<String> lobbies = FXCollections.observableArrayList(lobbyPlayersMap.keySet());
        lobbiesListView.setItems(lobbies);
    }
    private void updatePlayersListView(String lobbyName) {
        List<String> players = lobbyPlayersMap.getOrDefault(lobbyName, Collections.emptyList());
        ObservableList<String> playerNames = FXCollections.observableArrayList(players);
        playersListView.setItems(playerNames);
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
    private void processLobbies(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            lobbyPlayersMap.clear();

            rootNode.fields().forEachRemaining(entry -> {
                String lobbyName = entry.getKey();
                JsonNode lobbyNode = entry.getValue();
                JsonNode playersNode = lobbyNode.path("players");

                List<String> players = new ArrayList<>();
                for (JsonNode playerNode : playersNode) {
                    if (!playerNode.isNull()) {
                        players.add(playerNode.path("name").asText());
                    }
                }

                lobbyPlayersMap.put(lobbyName, players);
            });

            updateLobbiesListView();
        } catch (IOException e) {
            logger.error("Error processing JSON response.", e);
        }
    }
    @FXML
    private void joinLobby() {
        try {
            String selectedLobby = lobbiesListView.getSelectionModel().getSelectedItem();
            //String selectedLobby = "paypal";
            if (selectedLobby != null && !selectedLobby.isEmpty()) {
                String response = client.send("J " + selectedLobby);
                logger.info("ODESLANÝ PŘÍKAZ J " + selectedLobby);
                if (response != null) {
                    // Odstranění znaku 'J'
                    if (response.startsWith("J ")) {
                        response = response.substring(2);
                        logger.info("ODSTRANĚNÍ ZNAKU J");
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    logger.info("ZAČÁTEK ČTENÍ READTREE");
                    JsonNode lobbyNode = objectMapper.readTree(response);

                    // Kontrola, zda JSON obsahuje klíč 'name'
                    if (lobbyNode != null && lobbyNode.has("name")) {
                        logger.info("LOBBYNODE OBSAHUJE NAME, UPDATEPLAYERSLIST PLAYERS");
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