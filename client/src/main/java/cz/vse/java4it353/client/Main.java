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


public class Main extends Application {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static LobbyController lobbyController;
    private static Stage primaryStage;
    public static String playerName = "";

    public static void main(String[] args) {
        log.info("Starting JavaFX application");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
       /* // Načtení FXML souboru
        Parent root = FXMLLoader.load(getClass().getResource("/aplikace.fxml"));

        // Vytvoření scény
        Scene scene = new Scene(root, 703, 980);

        // Nastavení scény a zobrazení hlavního okna
        primaryStage.setTitle("JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();*/

        Main.primaryStage = primaryStage;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Login");
        dialog.setHeaderText("Zadejte své jméno");
        dialog.setContentText("Jméno:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            playerName = result.get();
            log.info("Logged name: " + playerName);
            new Thread(() -> {
                Client client = null;
                try {
                    client = Client.getInstance();
                    log.info("Created new thread, client: " + client);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                client.send("L " + playerName);
                log.info("Login command with name " + playerName + " has been sent");
            }).start();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/lobby.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lobby");
        primaryStage.show();
        log.info("lobby.fxml started");
    }

    @Override
    public void stop() throws Exception {
        Client.getInstance().closeConnection();
        super.stop();
    }

    public static void showLobby() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/lobby.fxml"));
                Scene scene = new Scene(loader.load());
                lobbyController = loader.getController(); // Get the controller instance
                primaryStage.setScene(scene);
                primaryStage.setTitle("Lobby");
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
