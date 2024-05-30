package cz.vse.java4it353.client.controllers;

import cz.vse.java4it353.client.Client;
import cz.vse.java4it353.client.Main;
import cz.vse.java4it353.client.MessageObserver;
import cz.vse.java4it353.client.commands.CommandFactory;
import cz.vse.java4it353.client.commands.ICommand;
import cz.vse.java4it353.client.enums.ColorEnum;
import cz.vse.java4it353.client.model.Board;
import cz.vse.java4it353.client.model.Lobby;
import cz.vse.java4it353.client.model.Player;
import cz.vse.java4it353.client.model.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class HomeController implements MessageObserver, Observer {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private ImageView selectedFigurka = null;
    private Map<ImageView, ImageView> poziceFigurky = new HashMap<>();
    private Map<String, ImageView> nasazeniPozice = new HashMap<>();
    private Map<String, List<Pair<Double, Double>>> startPositions = new HashMap<>();
    private Client client;
    private Lobby aktualniLobby = new Lobby();
    private Player aktualniPlayer = new Player();;
    private List<Lobby> allLobies = new ArrayList<>();
    private CommandFactory cf;
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
    private Map<String, ImageView> imageViewMap;
    public void initialize() {
        try {
            client = Client.getInstance();
            client.addObserver(this);
        } catch (IOException e) {
            log.error("Chyba při inicializaci klienta", e);
        }
        cf = new CommandFactory(this);
        aktualniLobby = Main.getLobby();

        imageViewMap = new HashMap<>();
        imageViewMap.put("c1xl31xm21xz11", c1xl31xm21xz11);
        imageViewMap.put("c2xl32xm22xz12", c2xl32xm22xz12);
        imageViewMap.put("c3xl33xm23xz13", c3xl33xm23xz13);
        imageViewMap.put("c4xl34xm24xz14", c4xl34xm24xz14);
        imageViewMap.put("c5xl35xm25xz15", c5xl35xm25xz15);
        imageViewMap.put("c6xl36xm26xz16", c6xl36xm26xz16);
        imageViewMap.put("c7xl37xm27xz17", c7xl37xm27xz17);
        imageViewMap.put("c8xl38xm28xz18", c8xl38xm28xz18);
        imageViewMap.put("c9xl39xm29xz19", c9xl39xm29xz19);
        imageViewMap.put("c10xl40xm30xz20", c10xl40xm30xz20);
        imageViewMap.put("c11xl1xm31xz21", c11xl1xm31xz21);
        imageViewMap.put("c12xl2xm32xz22", c12xl2xm32xz22);
        imageViewMap.put("c13xl3xm33xz23", c13xl3xm33xz23);
        imageViewMap.put("c14xl4xm34xz24", c14xl4xm34xz24);
        imageViewMap.put("c15xl5xm35xz25", c15xl5xm35xz25);
        imageViewMap.put("c16xl6xm36xz26", c16xl6xm36xz26);
        imageViewMap.put("c17xl7xm37xz27", c17xl7xm37xz27);
        imageViewMap.put("c18xl8xm38xz28", c18xl8xm38xz28);
        imageViewMap.put("c19xl9xm39xz29", c19xl9xm39xz29);
        imageViewMap.put("c20xl10xm40xz30", c20xl10xm40xz30);
        imageViewMap.put("c21xl11xm1xz31", c21xl11xm1xz31);
        imageViewMap.put("c22xl12xm2xz32", c22xl12xm2xz32);
        imageViewMap.put("c23xl13xm3xz33", c23xl13xm3xz33);
        imageViewMap.put("c24xl14xm4xz34", c24xl14xm4xz34);
        imageViewMap.put("c25xl15xm5xz35", c25xl15xm5xz35);
        imageViewMap.put("c26xl16xm6xz36", c26xl16xm6xz36);
        imageViewMap.put("c27xl17xm7xz37", c27xl17xm7xz37);
        imageViewMap.put("c28xl18xm8xz38", c28xl18xm8xz38);
        imageViewMap.put("c29xl19xm9xz39", c29xl19xm9xz39);
        imageViewMap.put("c30xl20xm10xz40", c30xl20xm10xz40);
        imageViewMap.put("c31xl21xm11xz1", c31xl21xm11xz1);
        imageViewMap.put("c32xl22xm12xz12", c32xl22xm12xz12);
        imageViewMap.put("c33xl23xm13xz3", c33xl23xm13xz3);
        imageViewMap.put("c34xl24xm14xz4", c34xl24xm14xz4);
        imageViewMap.put("c35xl25xm15xz5", c35xl25xm15xz5);
        imageViewMap.put("c36xl26xm16xz6", c36xl26xm16xz6);
        imageViewMap.put("c37xl27xm17xz7", c37xl27xm17xz7);
        imageViewMap.put("c38xl28xm18xz8", c38xl28xm18xz8);
        imageViewMap.put("c39xl29xm19xz9", c39xl29xm19xz9);
        imageViewMap.put("c40xl30xm20xz10", c40xl30xm20xz10);
        imageViewMap.put("c41", c41);
        imageViewMap.put("c42", c42);
        imageViewMap.put("c43", c43);
        imageViewMap.put("c44", c44);
        imageViewMap.put("m44", m44);
        imageViewMap.put("m43", m43);
        imageViewMap.put("m42", m42);
        imageViewMap.put("m41", m41);
        imageViewMap.put("l41", l41);
        imageViewMap.put("l42", l42);
        imageViewMap.put("l43", l43);
        imageViewMap.put("l44", l44);
        imageViewMap.put("z44", z44);
        imageViewMap.put("z43", z43);
        imageViewMap.put("z42", z42);
        imageViewMap.put("z41", z41);

        figurka1C.toFront();
        figurka2C.toFront();
        figurka3C.toFront();
        figurka4C.toFront();

        figurka1L.toFront();
        figurka2L.toFront();
        figurka3L.toFront();
        figurka4L.toFront();

        figurka1M.toFront();
        figurka2M.toFront();
        figurka3M.toFront();
        figurka4M.toFront();

        figurka1Z.toFront();
        figurka2Z.toFront();
        figurka3Z.toFront();
        figurka4Z.toFront();

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

        List<Pair<Double, Double>> cervenyHome = Arrays.asList(
                new Pair<>(figurka1C.getLayoutX(), figurka1C.getLayoutY()),
                new Pair<>(figurka2C.getLayoutX(), figurka2C.getLayoutY()),
                new Pair<>(figurka3C.getLayoutX(), figurka3C.getLayoutY()),
                new Pair<>(figurka4C.getLayoutX(), figurka4C.getLayoutY())
        );
        List<Pair<Double, Double>> zlutyHome = Arrays.asList(
                new Pair<>(figurka1L.getLayoutX(), figurka1L.getLayoutY()),
                new Pair<>(figurka2L.getLayoutX(), figurka2L.getLayoutY()),
                new Pair<>(figurka3L.getLayoutX(), figurka3L.getLayoutY()),
                new Pair<>(figurka4L.getLayoutX(), figurka4L.getLayoutY())
        );
        List<Pair<Double, Double>> modryHome = Arrays.asList(
                new Pair<>(figurka1M.getLayoutX(), figurka1M.getLayoutY()),
                new Pair<>(figurka2M.getLayoutX(), figurka2M.getLayoutY()),
                new Pair<>(figurka3M.getLayoutX(), figurka3M.getLayoutY()),
                new Pair<>(figurka4M.getLayoutX(), figurka4M.getLayoutY())
        );
        List<Pair<Double, Double>> zelenyHome = Arrays.asList(
                new Pair<>(figurka1Z.getLayoutX(), figurka1Z.getLayoutY()),
                new Pair<>(figurka2Z.getLayoutX(), figurka2Z.getLayoutY()),
                new Pair<>(figurka3Z.getLayoutX(), figurka3Z.getLayoutY()),
                new Pair<>(figurka4Z.getLayoutX(), figurka4Z.getLayoutY())
        );

        startPositions.put("C", cervenyHome);
        startPositions.put("L", zlutyHome);
        startPositions.put("M", modryHome);
        startPositions.put("Z", zelenyHome);
        firstStart();
    }

    int chosenToken = 0;
    private void handleFigurkaClick(MouseEvent event) {
        selectedFigurka = (ImageView) event.getSource();
        String zvolenaFigurka = selectedFigurka.getId();
        chosenToken = Integer.parseInt(zvolenaFigurka.substring(7, 8)) - 1; // "figurka1C" -> "1" -> token 0

        client.send("M " + chosenToken);
    }
    public void hodKostkou(ActionEvent actionEvent) {
        client.send("R");
    }
    private ImageView getFigurka(String colour, int token) {
        log.debug("Poslal jsem informace o barvě " + colour + "s tokenem " + token);
        switch (colour.toUpperCase()) {
            case "RED":
                switch (token) {
                    case 0: return figurka1C;
                    case 1: return figurka2C;
                    case 2: return figurka3C;
                    case 3: return figurka4C;
                }
                break;
            case "YELLOW":
                switch (token) {
                    case 0: return figurka1L;
                    case 1: return figurka2L;
                    case 2: return figurka3L;
                    case 3: return figurka4L;
                }
                break;
            case "BLUE":
                switch (token) {
                    case 0: return figurka1M;
                    case 1: return figurka2M;
                    case 2: return figurka3M;
                    case 3: return figurka4M;
                }
                break;
            case "GREEN":
                switch (token) {
                    case 0: return figurka1Z;
                    case 1: return figurka2Z;
                    case 2: return figurka3Z;
                    case 3: return figurka4Z;
                }
                break;
        }
        return null;
    }
    private ImageView getImageView(String colour, int position) {
        if(colour.equalsIgnoreCase("red")) {
            colour = "c";
        }
        else if(colour.equalsIgnoreCase("yellow")) {
            colour = "l";
        }
        else if(colour.equalsIgnoreCase("blue")) {
            colour = "m";
        }
        else if(colour.equalsIgnoreCase("green")) {
            colour = "z";
        }
        String key = colour.toLowerCase().charAt(0) + Integer.toString(position) + "x"; // PROBLÉM SE ZELENOU BARVOU, Z1X NEEXISTUJE
        ImageView obrazek;
        log.debug("Klíč, podle kterého hledat: " + key);
        for (Map.Entry<String, ImageView> entry : imageViewMap.entrySet()) {
            String klic = entry.getKey();
            if(klic.contains(key)) {
                obrazek = entry.getValue();
                return obrazek;
            }
        }
        return null;
    }
    private void moveTokens() {
        log.info("Započala metoda pro přesun");
        // Posun figurky na pozici, kterou mi vrátí server - musím si jí vypočítat sám
        // c1xl31xm21xz11
        // c1 = Červená barva 1. políčko
        // l31 = Žlutá barva 31. políčko
        // m21 = Modrá barva 21. políčko
        // z11 = Zelená barva 11. políčko

        Board aktualniDeska = aktualniLobby.getBoardState();

        List<Player> aktualniHraci = aktualniDeska.getPlayerMap().values().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        String barvaAktualniHrac;
        ImageView figurka;
        ImageView poziceNaDesce;
        int poziceTokenu = 0;
        int aktualniToken = -1;

        for(Player aktualniHrac : aktualniHraci) {
            barvaAktualniHrac = aktualniDeska.getPlayerColour(aktualniHrac.getName());
            for (Token token : aktualniHrac.getTokens()) {
                aktualniToken++;
                poziceTokenu = token.getPosition();
                log.debug("Pozice tokenu: " + poziceTokenu);
                if(poziceTokenu == 0) {
                    log.debug("Token je nula, nic tedy nepřesouvám");
                    continue;
                }
                log.debug("Token není nula");
                figurka = getFigurka(barvaAktualniHrac, aktualniToken);
                log.debug("Získaná figurka: " + figurka.getId());
                log.debug("Aktuální token: " + chosenToken);
                poziceNaDesce = getImageView(barvaAktualniHrac, poziceTokenu);

                figurka.setLayoutX(poziceNaDesce.getLayoutX());
                figurka.setLayoutY(poziceNaDesce.getLayoutY());
            }
            aktualniToken = 0;
        }
    }

    public void odeslat(ActionEvent actionEvent) throws IOException {
        /*String message = vstupTextField.getText();
        String command = "H " + message;
        Client client = Client.getInstance();
        client.send(command);
        vstupTextField.clear();*/
        int i = 0;
        log.info("Vypíšu informace o lobby");
        log.debug("Název lobby: " + aktualniLobby.getName());
        for(Player player : aktualniLobby.getPlayers()) {
            if(player != null) {
                log.debug("Jméno hráče: " + player.getName());
                i = 0;
                for(Token token : player.getTokens()) {
                    log.debug("Token hráče: " + i + "-" + token.getPosition());
                    i++;
                }
            }
        }
        log.debug("Kolik je dice value: " + aktualniLobby.getBoardState().getDiceValue());
        log.debug("Hráč na řadě: " + aktualniLobby.getBoardState().getPlayerOnTurn().getName());
    }
    private String response;
    @FXML
    private void firstStart() {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("Ahoj! Zde jsou informace o lobby, které se musí zobrazit všem hráčům!");
        sb.append("\nNázev lobby: " + aktualniLobby.getName());
        for (Player player : aktualniLobby.getPlayers()) {
            if(player != null) {
                sb.append("\n");
                sb.append("Název " + i + ". hráče: " + player.getName() + ", jeho barva: " + aktualniLobby.getBoardState().getPlayerColour(player.getName()));
                i++;
            }
        }
        sb.append("\nTento klient je na tahu: " + (aktualniLobby.getBoardState().getPlayerOnTurn().getName().equals(Main.getPlayerName()) ? true : false));
        sb.append("\nPlayerOnTurn: " + aktualniLobby.getBoardState().getPlayerOnTurn().getName());
        chatTextArea.setText(sb.toString());
    }

    @Override
    public void onMessageReceived(String message) {
        log.debug("Přijatá zpráva v HomeControlleru: " + message);
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
    private void handleResponseFromServer(String data) throws Exception {
        log.debug("string před rozdělením: " + data);
        String[] handledData = data.split(" ", 2);
        ICommand command;
        if(handledData.length == 1) {
            command = cf.getCommand("L");
            command.execute(handledData[0]);
        }
        else {
            command = cf.getCommand(handledData[0]);
            command.execute(handledData[1]);
        }
    }

    private Lobby placeHolderLobby = new Lobby();
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Board) {
            Board nova = (Board) arg;

            placeHolderLobby = aktualniLobby;
            aktualniLobby.setBoardState(nova);

            moveTokens();
        }
    }
}
