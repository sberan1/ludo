package cz.vse.java4it353.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private PrintWriter pw;
    private BufferedReader in;
    // Using a fixed thread pool for managing multiple threads
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

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
            pw = client.pw;
            in = client.in;

            String command = "L " + Main.playerName;
            String response = client.send(command);
            if (response != null) {
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
        logger.debug("Players in updatePlayersListView: " + players.toString());
        ObservableList<String> playerNames = FXCollections.observableArrayList(players);
        logger.debug("Player names in updatePlayersListView: " + playerNames.toString());
        playersListView.setItems(playerNames);
        logger.debug("playersListView: " + playersListView);
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

                    // Přidání hráče do lobby
                    List<String> players = new ArrayList<>();
                    players.add(Main.playerName);
                    lobbyPlayersMap.put(lobbyNameFromResponse, players);

                    updatePlayersListView(lobbyNameFromResponse);
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

            // Zpracování hlavního uzlu JSON odpovědi
            if (rootNode.isObject()) {
                rootNode.fields().forEachRemaining(entry -> {
                    String lobbyName = entry.getKey();
                    JsonNode lobbyNode = entry.getValue();

                    JsonNode playersNode = lobbyNode.path("players");

                    List<String> players = new ArrayList<>();
                    for (JsonNode playerNode : playersNode) {
                        if (playerNode != null && playerNode.has("name")) {
                            players.add(playerNode.path("name").asText());
                        }
                    }

                    lobbyPlayersMap.put(lobbyName, players);
                });
            }

            updateLobbiesListView();
        } catch (IOException e) {
            logger.error("Error processing JSON response.", e);
        }
    }

    @FXML
    private void joinLobby() {
        String selectedLobby = lobbiesListView.getSelectionModel().getSelectedItem();
        logger.debug("Selected lobby: " + selectedLobby);
        if (selectedLobby != null && !selectedLobby.isEmpty()) {
            Task<String> task = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return client.send("J " + selectedLobby);
                }
            };

            task.setOnSucceeded(event -> {
                String response = task.getValue();
                logger.debug("Server response in LobbyController: " + response);
                // Handle the server response
                if (response != null) {
                    if (response.startsWith("J ")) {
                        response = response.substring(2);
                        logger.info("ODSTRANĚNÍ ZNAKU J");
                        logger.debug("Server response in LobbyController after J-purge: " + response);
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    logger.info("ZAČÁTEK ČTENÍ READTREE");
                    try {
                        JsonNode lobbyNode = objectMapper.readTree(response);
                        logger.debug("Lobby node: " + lobbyNode.asText());

                        // Kontrola, zda JSON obsahuje klíč 'name'
                        if (lobbyNode != null && lobbyNode.has("name")) {
                            String lobbyName = lobbyNode.get("name").asText();
                            logger.debug("Lobby name: " + lobbyName);

                            // Aktualizace lobbyPlayersMap
                            List<String> players = new ArrayList<>();
                            JsonNode playersNode = lobbyNode.get("players");
                            logger.debug("All players node: " + playersNode.asText());
                            for (JsonNode playerNode : playersNode) {
                                if (!playerNode.isNull()) {
                                    logger.debug("Player: " + playerNode.asText());
                                    players.add(playerNode.path("name").asText());
                                }
                            }
                            lobbyPlayersMap.put(lobbyName, players);
                            logger.debug("Players map: " + lobbyPlayersMap.toString());

                            // Aktualizace playersListView
                            logger.debug("Updating players list view");
                            updatePlayersListView(lobbyName);
                        } else {
                            logger.error("JSON response does not contain expected 'name' field: " + response);
                        }
                    } catch (IOException e) {
                        logger.error("Error processing JSON response.", e);
                    }
                }
            });

            task.setOnFailed(event -> {
                Throwable e = task.getException();
                e.printStackTrace();
                // Handle the error
            });

            executorService.submit(task);
        }
    }

    private void updatePlayersList(JsonNode players) {
        playersListView.getItems().clear();
        for (JsonNode player : players) {
            if (player != null && player.has("name")) {
                playersListView.getItems().add(player.get("name").asText());
            }
        }
    }

    public String send(String data) {
        pw.println(data);
        logger.info("Command sent: " + data);
        return receive();
    }

    private String receive() {
        StringBuilder fullResponse = new StringBuilder();
        String response;
        try {
            while ((response = in.readLine()) != null) {
                fullResponse.append(response);
            }
            logger.info("Server response: " + fullResponse.toString());
            return fullResponse.toString();
        } catch (IOException e) {
            logger.error("Error receiving response.", e);
            return null;
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }

    public void stop() {
        executorService.shutdown();
    }
}