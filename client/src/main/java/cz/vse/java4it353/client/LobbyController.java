package cz.vse.java4it353.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.client.model.Lobby;
import cz.vse.java4it353.client.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class LobbyController {

    private static final Logger logger = LoggerFactory.getLogger(LobbyController.class);
    @FXML
    public RadioButton rbCervena;
    @FXML
    public RadioButton rbZluta;
    @FXML
    public RadioButton rbModra;
    @FXML
    public RadioButton rbZelena;
    @FXML
    public Button buttonChooseColor;
    @FXML
    public Label labelColor;
    private Map<String, List<String>> lobbyPlayersMap = new HashMap<>();
    private List<Lobby> allLobies = new ArrayList<>();
    private static LobbyController instance;
    private Client client;
    private Lobby ogLobby;
    private Player ogPlayer = new Player();
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
    private String offLobbyName = "";
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public LobbyController() {
        instance = this;
    }

    public static LobbyController getInstance() {
        return instance;
    }

    public void initialize() {
        try {
            client = Client.getInstance();
            pw = client.pw;
            in = client.in;
            startServerListener();
            lobbiesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    handleLobbySelection(newValue);
                }
            });


        } catch (IOException e) {
            logger.error("Špatná inicializace LobbyController: " + e.getMessage());
        }
    }
    private void startServerListener() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String message;
                while ((message = in.readLine()) != null) {
                    String finalMessage = message;
                    Platform.runLater(() -> handleServerResponse(finalMessage));
                }
                return null;
            }
        };

        task.setOnFailed(event -> {
            Throwable e = task.getException();
            logger.error("Failed to listen to server", e);
        });

        executorService.submit(task);
    }

    private void handleLobbySelection(String selectedLobbyName) {
        logger.debug("Započata metoda handleLobbySelection");
        Lobby selectedLobby = allLobies.stream()
                .filter(lobby -> lobby.getName().equals(selectedLobbyName))
                .findFirst()
                .orElse(null);
        logger.debug("Název zvolené lobby: " + selectedLobby.getName());
        if (selectedLobby != null) {
            List<Player> players = selectedLobby.getPlayers();
            List<String> playerNames = players.stream()
                    .filter(player -> player != null)
                    .map(Player::getName)
                    .collect(Collectors.toList());
            for (String name : playerNames) {
                logger.debug("Název osoby v lobby: " + name);
            }
            ObservableList<String> observablePlayerNames = FXCollections.observableArrayList(playerNames);
            playersListView.setItems(observablePlayerNames);
        }
    }


    private void updateLobbiesListView() {
        List<String> lobbyNames = allLobies.stream()
                .map(Lobby::getName)
                .collect(Collectors.toList());
        ObservableList<String> observableLobbyNames = FXCollections.observableArrayList(lobbyNames);
        lobbiesListView.setItems(observableLobbyNames);
    }

    @FXML
    private void handleChooseColor() {
        String color = "RED";
        if(rbZluta.isSelected()) color = "YELLOW";
        else if(rbModra.isSelected()) color = "BLUE";
        else if(rbZelena.isSelected()) color = "GREEN";

        String finalColor = color;
        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                String command = "CC " + finalColor;
                return client.send(command);
            }
        };

        task.setOnSucceeded(event -> {
            logger.debug("Úspěšný task handleChooseColor");
            labelColor.setText(labelColor.getText() + finalColor);
        });

        task.setOnFailed(event -> {
            Throwable e = task.getException();
            logger.error("Failed to send color choice", e);
        });

        executorService.submit(task);
    }

    @FXML
    private void handleStartGame() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                client.send("S " + offLobbyName);
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            logger.info("Start game command sent");
            Platform.runLater(() -> {
                // Close the current window and show the game window
                Stage stage = (Stage) playersListView.getScene().getWindow();
                stage.close();

                // Show aplikace.fxml
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplikace.fxml"));
                    Stage gameStage = new Stage();
                    gameStage.setScene(new Scene(loader.load()));
                    gameStage.setTitle("Game");
                    gameStage.show();
                } catch (IOException e) {
                    logger.error("Error loading aplikace.fxml", e);
                }
            });
        });

        task.setOnFailed(event -> {
            Throwable e = task.getException();
            logger.error("Failed to send start game command", e);
        });

        executorService.submit(task);
    }

    @FXML
    private void createLobby() {
        try {
            String lobbyName = lobbyNameInput.getText();
            Task<String> task = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return client.send("C " + lobbyName);
                }
            };

            task.setOnSucceeded(event -> {
                String response = task.getValue();
                handleServerResponse(response);
            });

            task.setOnFailed(event -> {
                Throwable e = task.getException();
                logger.error("Failed to send create lobby command", e);
            });

            executorService.submit(task);
        } catch (Exception e) {
            logger.error("Failed to send create lobby command.", e);
        }
    }

    @FXML
    private void joinLobby() {
        String selectedLobby = lobbiesListView.getSelectionModel().getSelectedItem();
        offLobbyName = selectedLobby;
        logger.debug("Selected lobby: " + selectedLobby);
        if (selectedLobby != null && !selectedLobby.isEmpty()) {
            Task<String> task = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    String command = "J " + selectedLobby;
                    return client.send(command);
                }
            };

            task.setOnSucceeded(event -> {
                String response = task.getValue();
                handleServerResponse(response);
            });

            task.setOnFailed(event -> {
                Throwable e = task.getException();
                logger.error("Failed to join lobby", e);
            });

            executorService.submit(task);
        }
    }

    public void stop() {
        executorService.shutdown();
    }

    public void refresh(ActionEvent actionEvent) {
        logger.debug(client.getFirstResponse());
        handleServerResponse(client.getFirstResponse());
    }
    private void handleServerResponse(String data) {
        boolean dataIsNotNull = data != null;
        boolean dataStartsWithLOrJ = (data.startsWith("L ") || data.startsWith("J "));
        if (dataIsNotNull && dataStartsWithLOrJ) {
            data = data.substring(2); // Odstraňte prefix "L " nebo "J "
            logger.debug("Processed data: " + data);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                // Zjistíme, zda je JSON mapou nebo jednotlivým objektem
                JsonNode jsonNode = objectMapper.readTree(data);
                Lobby lobby = null;

                if (jsonNode.isObject() && jsonNode.size() == 1) {
                    // JSON je mapa
                    Map<String, Lobby> lobbyMap = objectMapper.convertValue(jsonNode, new TypeReference<Map<String, Lobby>>() {});
                    lobby = lobbyMap.values().iterator().next();
                } else if (jsonNode.isObject()) {
                    // JSON je jednotlivý objekt
                    lobby = objectMapper.convertValue(jsonNode, Lobby.class);
                }

                if (lobby != null) {
                    final Lobby finalLobby = lobby;
                    Platform.runLater(() -> {
                        allLobies.add(finalLobby);
                        updateLobbiesListView();
                    });
                }

            } catch (JsonMappingException ex) {
                logger.error("JSONMAPPINGEXCEPTION", ex);
                logger.error("Data: " + data);
            } catch (JsonProcessingException ex) {
                logger.error("JSONPROCESSINGEXCEPTION", ex);
                logger.error("Data: " + data);
            } catch (IOException e) {
                logger.error("Error processing JSON response.", e);
                logger.error("Data: " + data);
            }
        }
    }
}