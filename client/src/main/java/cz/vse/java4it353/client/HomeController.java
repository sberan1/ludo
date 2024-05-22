package cz.vse.java4it353.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;


public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private int moveToken = 0;
    private String name = "";
    private ImageView selectedFigurka = null;
    private Map<ImageView, ImageView> poziceFigurky = new HashMap<>();
    private Map<String, ImageView> nasazeniPozice = new HashMap<>();
    private Map<String, List<Pair<Double, Double>>> startPositions = new HashMap<>();
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
    @FXML
    private ImageView c1xl31xm21xz11;
    @FXML
    private ImageView c2xl32xm22xz12;
    @FXML
    private ImageView c3xl33xm23xz13;
    @FXML
    private ImageView c4xl34xm24xz14;
    @FXML
    private ImageView c5xl35xm25xz15;
    @FXML
    private ImageView c6xl36xm26xz16;
    @FXML
    private ImageView c7xl37xm27xz17;
    @FXML
    private ImageView c8xl38xm28xz18;
    @FXML
    private ImageView c9xl39xm29xz19;
    @FXML
    private ImageView c10xl40xm30xz20;
    @FXML
    private ImageView c11xl1xm31xz21;
    @FXML
    private ImageView c12xl2xm32xz22;
    @FXML
    private ImageView c13xl3xm33xz23;
    @FXML
    private ImageView c14xl4xm34xz24;
    @FXML
    private ImageView c15xl5xm35xz25;
    @FXML
    private ImageView c16xl6xm36xz26;
    @FXML
    private ImageView c17xl7xm37xz27;
    @FXML
    private ImageView c18xl8xm38xz28;
    @FXML
    private ImageView c19xl9xm39xz29;
    @FXML
    private ImageView c20xl10xm40xz30;
    @FXML
    private ImageView c21xl11xm1xz31;
    @FXML
    private ImageView c22xl12xm2xz32;
    @FXML
    private ImageView c23xl13xm3xz33;
    @FXML
    private ImageView c24xl14xm4xz34;
    @FXML
    private ImageView c25xl15xm5xz35;
    @FXML
    private ImageView c26xl16xm6xz36;
    @FXML
    private ImageView c27xl17xm7xz37;
    @FXML
    private ImageView c28xl18xm8xz38;
    @FXML
    private ImageView c29xl19xm9xz39;
    @FXML
    private ImageView c30xl20xm10xz40;
    @FXML
    private ImageView c31xl21xm11xz1;
    @FXML
    private ImageView c32xl22xm12xz12;
    @FXML
    private ImageView c33xl23xm13xz3;
    @FXML
    private ImageView c34xl24xm14xz4;
    @FXML
    private ImageView c35xl25xm15xz5;
    @FXML
    private ImageView c36xl26xm16xz6;
    @FXML
    private ImageView c37xl27xm17xz7;
    @FXML
    private ImageView c38xl28xm18xz8;
    @FXML
    private ImageView c39xl29xm19xz9;
    @FXML
    private ImageView c40xl30xm20xz10;
    @FXML
    private ImageView c41;
    @FXML
    private ImageView c42;
    @FXML
    private ImageView c43;
    @FXML
    private ImageView c44;
    @FXML
    private ImageView m44;
    @FXML
    private ImageView m43;
    @FXML
    private ImageView m42;
    @FXML
    private ImageView m41;
    @FXML
    private ImageView l41;
    @FXML
    private ImageView l42;
    @FXML
    private ImageView l43;
    @FXML
    private ImageView l44;
    @FXML
    private ImageView z44;
    @FXML
    private ImageView z43;
    @FXML
    private ImageView z42;
    @FXML
    private ImageView z41;
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

        nasazeniPozice.put("C", c1xl31xm21xz11);
        nasazeniPozice.put("L", c11xl1xm31xz21);
        nasazeniPozice.put("M", c21xl11xm1xz31);
        nasazeniPozice.put("Z", c31xl21xm11xz1);

        List<Pair<Double, Double>> cerveny = Arrays.asList(
                new Pair<>(figurka1C.getLayoutX(), figurka1C.getLayoutY()),
                new Pair<>(figurka2C.getLayoutX(), figurka2C.getLayoutY()),
                new Pair<>(figurka3C.getLayoutX(), figurka3C.getLayoutY()),
                new Pair<>(figurka4C.getLayoutX(), figurka4C.getLayoutY())
        );
        List<Pair<Double, Double>> zluty = Arrays.asList(
                new Pair<>(figurka1L.getLayoutX(), figurka1L.getLayoutY()),
                new Pair<>(figurka2L.getLayoutX(), figurka2L.getLayoutY()),
                new Pair<>(figurka3L.getLayoutX(), figurka3L.getLayoutY()),
                new Pair<>(figurka4L.getLayoutX(), figurka4L.getLayoutY())
        );
        List<Pair<Double, Double>> modry = Arrays.asList(
                new Pair<>(figurka1M.getLayoutX(), figurka1M.getLayoutY()),
                new Pair<>(figurka2M.getLayoutX(), figurka2M.getLayoutY()),
                new Pair<>(figurka3M.getLayoutX(), figurka3M.getLayoutY()),
                new Pair<>(figurka4M.getLayoutX(), figurka4M.getLayoutY())
        );
        List<Pair<Double, Double>> zeleny = Arrays.asList(
                new Pair<>(figurka1Z.getLayoutX(), figurka1Z.getLayoutY()),
                new Pair<>(figurka2Z.getLayoutX(), figurka2Z.getLayoutY()),
                new Pair<>(figurka3Z.getLayoutX(), figurka3Z.getLayoutY()),
                new Pair<>(figurka4Z.getLayoutX(), figurka4Z.getLayoutY())
        );

        startPositions.put("C", cerveny);
        startPositions.put("L", zluty);
        startPositions.put("M", modry);
        startPositions.put("Z", zeleny);
    }
    private void handleFigurkaClick(MouseEvent event) {
        /*
        selectedFigurka = (ImageView) event.getSource();
        String command = "M " + moveToken;
        Client client = Client.getInstance();
        client.send(command);

        selectedFigurka = null;
        */

        String command = "L " + Math.random() * 100;
        Client client = null;
        try {
            client = Client.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.send(command);
    }
    public void hodKostkou(ActionEvent actionEvent) throws IOException {
        String command = "R";
        Client client = Client.getInstance();
        client.send(command);
        moveToken = 1; // Zpracování odpovědi serveru
    }
    private void handlePositionClick(MouseEvent event) {
        // Posun figurky na pozici, kterou mi vrátí server - musím si jí vypočítat sám
        // c1xl31xm21xz11
        // c1 = Červená barva 1. políčko
        // l31 = Žlutá barva 31. políčko
        // m21 = Modrá barva 21. políčko
        // z11 = Zelená barva 11. políčko

    }

    public void odeslat(ActionEvent actionEvent) throws IOException {
        String message = vstupTextField.getText();
        String command = "H " + message;
        Client client = Client.getInstance();
        client.send(command);
        vstupTextField.clear();
    }
    private void nasazeniFigurky() {
        String zvolenaFigurka = selectedFigurka.getId();
        String barva = String.valueOf(zvolenaFigurka.charAt(zvolenaFigurka.length() - 1));

        /*List<Pair<Double, Double>> startovniPozice = startPositions.get(barva);
        if(startovniPozice == null) {
            return;
        }
        for (Pair<Double, Double> startPosition: startovniPozice)
        {
            if(startPosition.getKey() == selectedFigurka.getLayoutX() &&
                    startPosition.getValue() == selectedFigurka.getLayoutY())
            {

            }
        }*/

        ImageView startPosition = nasazeniPozice.get(barva);

        if(selectedFigurka.getLayoutX() == 0)
        if(startPosition != null) {
            selectedFigurka.setLayoutX(startPosition.getLayoutX());
            selectedFigurka.setLayoutY(startPosition.getLayoutY());
        }
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
