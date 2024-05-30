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
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class HomeController implements MessageObserver, Observer {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @FXML
    public Label labelRedPlayer;
    @FXML
    public Label labelYellowPlayer;
    @FXML
    public Label labelBluePlayer;
    @FXML
    public Label labelGreenPlayer;
    private ImageView selectedFigurka = null;
    private Map<String, ImageView> nasazeniPozice = new HashMap<>();
    private Map<String, List<Pair<Double, Double>>> startPositions = new HashMap<>();
    private Client client;
    private Lobby aktualniLobby = new Lobby();
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
    private String barvaHrace;
    public void initialize() {
        try {
            client = Client.getInstance();
            client.addObserver(this);
        } catch (IOException e) {
            log.error("Chyba při inicializaci klienta", e);
        }
        cf = new CommandFactory(this);
        aktualniLobby = Main.getLobby();
        if(aktualniLobby != null && aktualniLobby.getPlayers() != null) {
            barvaHrace = aktualniLobby.getPlayers().stream()
                    .filter(Objects::nonNull) // Pouze ne-null hráče
                    .filter(player -> Main.getPlayerName().equals(player.getName())) // Hráč podle jména
                    .map(player -> aktualniLobby.getBoardState().getPlayerColour(player.getName())) // Mapuje hráče na barvu
                    .findFirst() // Najde první shodu
                    .orElse(null); // Vrátí barvu nebo null, pokud žádný hráč neodpovídá
        }
        if(barvaHrace.equalsIgnoreCase("red")) barvaHrace = "c";
        else if(barvaHrace.equalsIgnoreCase("yellow")) barvaHrace = "l";
        else if(barvaHrace.equalsIgnoreCase("blue")) barvaHrace = "m";
        else if(barvaHrace.equalsIgnoreCase("green")) barvaHrace = "z";

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

        startPositions.put("c", cervenyHome);
        startPositions.put("l", zlutyHome);
        startPositions.put("m", modryHome);
        startPositions.put("z", zelenyHome);
        diceImages = new ArrayList<>();
        Image obrazek;
        String adresa;
        for (int i = 1; i <= 6; i++) {
            adresa = "/kostka/" + i + ".png";
            log.debug("Pokus o nahrání obrázku, adresa: " + adresa);

            obrazek = new Image(getClass().getResourceAsStream(adresa));
            log.debug("Název obrázku: " + obrazek.getUrl());
            diceImages.add(obrazek);

        }
        firstStart();
    }

    int chosenToken = 0;
    private void handleFigurkaClick(MouseEvent event) {
        selectedFigurka = (ImageView) event.getSource();
        String zvolenaFigurka = selectedFigurka.getId();
        String barvaFigurky = zvolenaFigurka.substring(8, 9);
        if(!barvaFigurky.equalsIgnoreCase(barvaHrace)) {
            return;
        }

        chosenToken = Integer.parseInt(zvolenaFigurka.substring(7, 8)) - 1; // "figurka1C" -> "1" -> token 0

        client.send("M " + chosenToken);
        chatTextArea.appendText("\nServer: Hráč " + hracNaTahu + " právě odehrál svůj tah!");
    }
    private List<Image> diceImages;
    private Random random = new Random();
    private boolean rolledDice = false;
    public void hodKostkou(ActionEvent actionEvent) {
        log.info("Byla spuštěna metoda hoď kostkou");
        Task<Void> diceRollTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                int rolls = 5 + random.nextInt(6); // Roll between 5 and 10 times
                for (int i = 0; i < rolls; i++) {
                    Platform.runLater(() -> kostkaImageView.setImage(diceImages.get(random.nextInt(6))));
                    Thread.sleep(100); // Wait for 100 milliseconds between each roll
                }

                // Send the roll command to the server
                client.send("R");

                return null;
            }
        };

        // Start the dice roll task in a new thread
        log.info("Spouštím vlákno pro roll kostkou");
        new Thread(diceRollTask).start();

    }
    private ImageView getFigurka(String colour, int token) {
        log.debug("Poslal jsem informace o barvě " + colour + " s tokenem " + token);
        switch (colour.toUpperCase()) {
            case "RED":
                log.info("Vybírám z barvy ČERVENÁ");
                switch (token) {
                    case 0: return figurka1C;
                    case 1: return figurka2C;
                    case 2: return figurka3C;
                    case 3: return figurka4C;
                }
                break;
            case "YELLOW":
                log.info("Vybírám z barvy ŽLUTÁ");
                switch (token) {
                    case 0: return figurka1L;
                    case 1: return figurka2L;
                    case 2: return figurka3L;
                    case 3: return figurka4L;
                }
                break;
            case "BLUE":
                log.info("Vybírám z barvy MODRÁ");
                switch (token) {
                    case 0: return figurka1M;
                    case 1: return figurka2M;
                    case 2: return figurka3M;
                    case 3: return figurka4M;
                }
                break;
            case "GREEN":
                log.info("Vybírám z barvy ZELENÁ");
                switch (token) {
                    case 0: return figurka1Z;
                    case 1: return figurka2Z;
                    case 2: return figurka3Z;
                    case 3: return figurka4Z;
                }
                break;
        }
        log.debug("NEBYLA ZVOLENA ŽÁDNÁ FIGURKA");
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
        List<Pair<Double, Double>> pary = new ArrayList<>();

        for(Player aktualniHrac : aktualniHraci) {
            aktualniToken = -1;
            for (Token token : aktualniHrac.getTokens()) {
                barvaAktualniHrac = aktualniDeska.getPlayerColour(aktualniHrac.getName()); // BARVA MUSÍ BEJT JEŠTĚ NĚKDE
                aktualniToken++;
                log.debug("TOKEN PO ZVÝŠENÍ: " + aktualniToken);
                poziceTokenu = token.getPosition();
                figurka = getFigurka(barvaAktualniHrac, aktualniToken);
                if(figurka == null) continue;
                if(poziceTokenu == 0) {
                    if(barvaAktualniHrac.equalsIgnoreCase("red")) {
                        barvaAktualniHrac = "c";
                    }
                    else if(barvaAktualniHrac.equalsIgnoreCase("yellow")) {
                        barvaAktualniHrac = "l";
                    }
                    else if(barvaAktualniHrac.equalsIgnoreCase("blue")) {
                        barvaAktualniHrac = "m";
                    }
                    else if(barvaAktualniHrac.equalsIgnoreCase("green")) {
                        barvaAktualniHrac = "z";
                    }
                    log.debug("Pozice tokenu je 0, barva hráče je " + barvaAktualniHrac);
                    pary = startPositions.get(barvaAktualniHrac);
                    log.info("Získal jsem páry startujících pozicí barvy" + barvaAktualniHrac);
                    if(token.getPosition() == 0) {
                        log.info("Hodnota tokenu je 0");
                        int numOfFigurkaMinusOne = Integer.parseInt(figurka.getId().substring(7, 8)) - 1;
                        log.debug("Číslo figurky, na které jsem klikl, je " + (numOfFigurkaMinusOne + 1) + ", ale byla snížena na " + numOfFigurkaMinusOne);
                        Pair<Double, Double> par = pary.get(numOfFigurkaMinusOne);
                        log.debug("Byl získán pár, hodnota x: " + par.getKey() + ", hodnota y: " + par.getValue());
                        figurka.setLayoutX(par.getKey());
                        figurka.setLayoutY(par.getValue());
                        log.debug("Layout figurky nastaven");
                    }
                    continue;
                }
                poziceNaDesce = getImageView(barvaAktualniHrac, poziceTokenu);
                if(poziceNaDesce == null) continue;

                figurka.setLayoutX(poziceNaDesce.getLayoutX() + poziceNaDesce.getFitWidth() / 4);
                figurka.setLayoutY(poziceNaDesce.getLayoutY() + poziceNaDesce.getFitHeight() / 4);
            }
        }
    }

    public void odeslat(ActionEvent actionEvent) throws IOException {
        /*String message = vstupTextField.getText();
        String command = "H " + message;
        client.send(command);
        vstupTextField.clear();*/
    }
    private String response;
    @FXML
    private void firstStart() {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("Zde jsou informace o lobby, které se musí zobrazit všem hráčům!");
        sb.append("\nNázev lobby: " + aktualniLobby.getName());
        for (Player player : aktualniLobby.getPlayers()) {
            if(player != null) {
                sb.append("\n");
                sb.append("Název " + i + ". hráče: " + player.getName() + ", jeho barva: " + aktualniLobby.getBoardState().getPlayerColour(player.getName()));
                i++;
            }
        }
        sb.append("\nPrvní hráč na tahu je " + aktualniLobby.getBoardState().getPlayerOnTurn().getName());
        chatTextArea.setText(sb.toString());

        String name;
        String colour;
        List<Player> aktualniHraci = aktualniLobby.getBoardState().getPlayerMap().values().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        labelRedPlayer.setText("");
        labelYellowPlayer.setText("");
        labelBluePlayer.setText("");
        labelGreenPlayer.setText("");
        for(Player hrac : aktualniHraci) {
            name = hrac.getName();
            colour = aktualniLobby.getBoardState().getPlayerColour(name);
            if(colour.equalsIgnoreCase("red")) labelRedPlayer.setText(name);
            else if(colour.equalsIgnoreCase("yellow")) labelYellowPlayer.setText(name);
            else if(colour.equalsIgnoreCase("blue")) labelBluePlayer.setText(name);
            else if(colour.equalsIgnoreCase("green")) labelGreenPlayer.setText(name);
        }
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
    private String hracNaTahu;
    private int hracHodilNaKostce = 0;
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Board) {
            Board nova = (Board) arg;

            placeHolderLobby = aktualniLobby;
            hracNaTahu = aktualniLobby.getBoardState().getPlayerOnTurn().getName();
            if(hracNaTahu.equals(Main.getPlayerName())) {
                log.info("Hráč je na tahu, nastavuji tedy obrázek");
                Platform.runLater(() -> kostkaImageView.setImage(diceImages.get(hracHodilNaKostce - 1)));
            }
            aktualniLobby.setBoardState(nova);
            hracHodilNaKostce = aktualniLobby.getBoardState().getDiceValue();

            moveTokens();
        }
    }
}
