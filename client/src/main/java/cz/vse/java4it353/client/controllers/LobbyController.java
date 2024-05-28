package cz.vse.java4it353.client.controllers;

import cz.vse.java4it353.client.Client;
import cz.vse.java4it353.client.Main;
import cz.vse.java4it353.client.MessageObserver;
import cz.vse.java4it353.client.commands.CommandFactory;
import cz.vse.java4it353.client.commands.ICommand;
import cz.vse.java4it353.client.model.Board;
import cz.vse.java4it353.client.model.Lobby;
import cz.vse.java4it353.client.model.Player;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LobbyController implements MessageObserver, Observer {

    private static final Logger log = LoggerFactory.getLogger(LobbyController.class);
    public static Stage primaryStage;
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
    private Lobby aktualniLobby;
    private Client client;
    private String color;
    private String selectedLobby;
    private CommandFactory cf;
    private String response;
    @FXML
    private ListView<String> playersListView;
    @FXML
    private ListView<String> lobbiesListView;
    @FXML
    public TextField lobbyNameInput;
    @FXML
    public Button joinLobbyButton;

    public LobbyController() {

    }
    public void initialize() {
        try {
            client = Client.getInstance();
            client.addObserver(this);
        } catch (IOException e) {
            log.error("Chyba při inicializaci klienta", e);
        }
        cf = new CommandFactory(this);
        lobbiesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleLobbySelection(newValue);
            }
        });
        client.send("L " + Main.getPlayerName());
    }
    private void handleLobbySelection(String selectedLobbyName) {
        this.selectedLobby = selectedLobbyName;
        log.debug("Započata metoda handleLobbySelection");
        Lobby selectedLobby = allLobies.stream()
                .filter(lobby -> lobby.getName().equals(selectedLobbyName))
                .findFirst()
                .orElse(null);
        log.debug("Název zvolené lobby: " + selectedLobby.getName());
        if (selectedLobby != null) {
            List<Player> players = selectedLobby.getPlayers();
            List<String> playerNames = players.stream()
                    .filter(player -> player != null)
                    .map(Player::getName)
                    .collect(Collectors.toList());
            for (String name : playerNames) {
                log.debug("Název osoby v lobby: " + name);
            }
            log.info("Zde ještě není spuštěn kód");
            ObservableList<String> observablePlayerNames = FXCollections.observableArrayList(playerNames);
            log.info("Zde byl spuštěn kód, nastavuji playersListView");
            playersListView.setItems(observablePlayerNames);
            log.info("Lidé nastavení");
        }
    }


    private void updateLobbiesListView() {
        List<String> lobbyNames = allLobies.stream()
                .map(Lobby::getName)
                .collect(Collectors.toList());
        ObservableList<String> observableLobbyNames = FXCollections.observableArrayList(lobbyNames);
        lobbiesListView.setItems(observableLobbyNames);
        labelColor.setText("Moje barva: " + color);
        lobbiesListView.getSelectionModel().clearSelection();
        playersListView.getSelectionModel().clearSelection();
        rbCervena.setSelected(false);
        rbZluta.setSelected(false);
        rbModra.setSelected(false);
        rbZelena.setSelected(false);
    }

    @FXML
    private void handleChooseColor() {
        boolean cervena = rbCervena.isSelected();
        boolean zluta = rbZluta.isSelected();
        boolean modra = rbModra.isSelected();
        boolean zelena = rbZelena.isSelected();
        String barva;
        if(cervena)
            barva = "RED";
        else if(zluta)
            barva = "YELLOW";
        else if(modra)
            barva = "BLUE";
        else if(zelena)
            barva = "GREEN";
        else return;
        color = barva;
        client.send("CC " + barva);
    }

    @FXML
    private void handleStartGame() {
        client.send("S " + aktualniLobby.getName());
    }

    @FXML
    private void createLobby() {
        client.send("C " + lobbyNameInput.getText());
        lobbyNameInput.setText("");
    }

    @FXML
    private void joinLobby() {
        client.send("J " + this.selectedLobby);
    }

    public void stop() {

    }

    public void refresh(ActionEvent actionEvent) {
        client.send("L ");
    }
    private void handleResponseFromServer(String data) throws Exception {
        log.debug("string před rozdělením: " + data);
        String[] handledData = data.split(" ", 2);
        ICommand command = cf.getCommand(handledData[0]);
        command.execute(handledData[1]);
    }

    @Override
    public void onMessageReceived(String message) {
        log.debug("Přijatá zpráva v LobbyControlleru: " + message);
        synchronized (this) {
            response = message;
        }
        log.info("Posílám data ke zpracování");
        try {
            handleResponseFromServer(response);
        } catch (Exception e) {
            log.error("There was a problem with json processing.", e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        int pocetLobbies = (int) allLobies.stream().count();
        log.debug("INFORMACE O LOBBIES PŘED UPDATE! POČET LOBBIES: " + pocetLobbies);

        if(arg instanceof Lobby) {
            Lobby newLobby = (Lobby) arg;
            if(pocetLobbies == 0) {
                allLobies.add(newLobby);
            }
            else {
                for(Lobby lobby: allLobies) {
                    if(lobby.getName().equalsIgnoreCase(newLobby.getName())) {
                        int index = allLobies.indexOf(lobby);
                        allLobies.remove(index);
                        allLobies.add(newLobby);
                    }
                }
                allLobies.add(newLobby);
            }
            aktualniLobby = newLobby; // Tohle snad bude fungovat
        }
        else if(arg instanceof List) {
            List<Lobby> newLobbies = (List<Lobby>) arg;
            allLobies = newLobbies;
        }
        else if(arg instanceof Board) {
            Platform.runLater(() -> {
                Board board = (Board) arg;
                aktualniLobby.setBoardState(board);


                // Načtení FXML souboru
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/aplikace.fxml"));
                } catch (IOException e) {
                    log.error("Ocitl se problém v načítání aplikace.fxml", e);
                }

                // Vytvoření scény
                Scene scene = new Scene(root, 703, 980);

                // Nastavení scény a zobrazení hlavního okna
                primaryStage.close();
                primaryStage.setTitle("Člověče, nezlob se! - " + Main.getPlayerName());
                primaryStage.setScene(scene);
                primaryStage.show();
                log.info("Spuštěna aplikace z aplikace.fxml se jménem " + Main.getPlayerName());
            });

            return;
        }

        log.debug("Pokus o spočítání počtu lobbies");
        pocetLobbies = (int) allLobies.stream().count();
        log.debug("INFORMACE O LOBBIES PO UPDATE! POČET LOBBIES: " + pocetLobbies);
        for(Lobby lobby: allLobies) {
            if(lobby != null) {
                log.debug("Název lobby: " + lobby.getName());
                log.debug("Název hráčů v lobby:");
                for(Player player: lobby.getPlayers()) {
                    if(player != null) {
                        log.debug(player.getName());
                    }
                }
            }
        }
        Platform.runLater(() -> { // Změna UI prvků musí proběhnout v hlavním vlákně
            updateLobbiesListView();
        });
    }
}