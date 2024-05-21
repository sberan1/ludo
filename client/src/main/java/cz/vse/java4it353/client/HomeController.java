package cz.vse.java4it353.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class HomeController {

    @FXML
    private Button hodKostkouButton;
    @FXML
    private ImageView kostkaImageView;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextField vstupTextField;
    @FXML
    private Button odeslatButton;
    @FXML
    private ImageView figurka1C;
    @FXML
    private ImageView figurka2C;
    @FXML
    private ImageView figurka3C;
    @FXML
    private ImageView figurka4C;
    @FXML
    private ImageView figurka1Z;
    @FXML
    private ImageView figurka2Z;
    @FXML
    private ImageView figurka3Z;
    @FXML
    private ImageView figurka4Z;
    @FXML
    private ImageView figurka1M;
    @FXML
    private ImageView figurka2M;
    @FXML
    private ImageView figurka3M;
    @FXML
    private ImageView figurka4M;
    @FXML
    private ImageView figurka1L;
    @FXML
    private ImageView figurka2L;
    @FXML
    private ImageView figurka3L;
    @FXML
    private ImageView figurka4L;
    private ImageView selectedFigurka = null;
    public void initialize() {
        figurka1C.setOnMouseClicked(this::handleFigurkaClick);
        figurka2C.setOnMouseClicked(this::handleFigurkaClick);
        figurka3C.setOnMouseClicked(this::handleFigurkaClick);
        figurka4C.setOnMouseClicked(this::handleFigurkaClick);
        figurka1Z.setOnMouseClicked(this::handleFigurkaClick);
        figurka2Z.setOnMouseClicked(this::handleFigurkaClick);
        figurka3Z.setOnMouseClicked(this::handleFigurkaClick);
        figurka4Z.setOnMouseClicked(this::handleFigurkaClick);
        figurka1M.setOnMouseClicked(this::handleFigurkaClick);
        figurka2M.setOnMouseClicked(this::handleFigurkaClick);
        figurka3M.setOnMouseClicked(this::handleFigurkaClick);
        figurka4M.setOnMouseClicked(this::handleFigurkaClick);
        figurka1L.setOnMouseClicked(this::handleFigurkaClick);
        figurka2L.setOnMouseClicked(this::handleFigurkaClick);
        figurka3L.setOnMouseClicked(this::handleFigurkaClick);
        figurka4L.setOnMouseClicked(this::handleFigurkaClick);
    }
    private void handleFigurkaClick(MouseEvent event) {
        selectedFigurka = (ImageView) event.getSource();
    }
    public void hodKostkou(ActionEvent actionEvent) {
        String command = "R";
        Client client = Client.getInstance();
        client.send(command);
    }
    private void posunFigurkou(MouseEvent event) {
        if (selectedFigurka != null) {
            ImageView clickedPosition = (ImageView) event.getSource();
            String command = "M " + selectedFigurka.getId() + " " + clickedPosition.getId();
            Client client = Client.getInstance();
            client.send(command);
        }
    }

    public void odeslat(ActionEvent actionEvent) {
        String message = vstupTextField.getText();
        String command = "H " + message;
        Client client = Client.getInstance();
        client.send(command);
        vstupTextField.clear();
    }

    /*
    commandMap.put("E", new DisconnectCommand(clientSocket));
        commandMap.put("L", new LoginCommand(clientSocket));
        commandMap.put("C", new CreateLobbyCommand(clientSocket, clientSockets));
        commandMap.put("J", new JoinLobbyCommand(clientSocket));
        commandMap.put("S", new StartGameCommand(clientSocket));
        commandMap.put("CC", new ChooseColorCommand(clientSocket));
        commandMap.put("R", new RollDiceCommand(clientSocket));
        commandMap.put("M", new MoveTokenCommand(clientSocket));
    */
}
