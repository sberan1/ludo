package cz.vse.java4it353.client.controllers;

import cz.vse.java4it353.client.Client;
import cz.vse.java4it353.client.Main;
import cz.vse.java4it353.client.MessageObserver;
import cz.vse.java4it353.client.commands.CommandFactory;
import cz.vse.java4it353.client.commands.ICommand;
import cz.vse.java4it353.client.enums.ColorEnum;
import cz.vse.java4it353.client.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class HomeController implements MessageObserver, Observer {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    public static Stage primaryStage;
    private boolean gameEnded = false;
    private ImageView selectedFigurka = null;
    private Map<String, List<Pair<Double, Double>>> startPositions = new HashMap<>();
    private Client client;
    private Lobby aktualniLobby = new Lobby();
    private CommandFactory cf;
    private Map<String, ImageView> imageViewMap;
    private String barvaHrace;
    int chosenToken = 0;
    private List<Image> diceImages;
    private Random random = new Random();
    private boolean rolledDice = false;
    private String response;
    private Lobby placeHolderLobby = new Lobby();
    private String hracNaTahu;
    private int hracHodilNaKostce = 0;
    private Map<ImageView, Timeline> animationMap = new HashMap<>(); // Mapa pro uložení animací
    @FXML
    public Label labelRedPlayer;
    @FXML
    public Label labelYellowPlayer;
    @FXML
    public Label labelBluePlayer;
    @FXML
    public Label labelGreenPlayer;
    @FXML
    private ImageView kostkaImageView;
    @FXML
    private TextArea chatTextArea;
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
    private ImageView c31xl21xm11xz01;
    @FXML
    private ImageView c32xl22xm12xz02;
    @FXML
    private ImageView c33xl23xm13xz03;
    @FXML
    private ImageView c34xl24xm14xz04;
    @FXML
    private ImageView c35xl25xm15xz05;
    @FXML
    private ImageView c36xl26xm16xz06;
    @FXML
    private ImageView c37xl27xm17xz07;
    @FXML
    private ImageView c38xl28xm18xz08;
    @FXML
    private ImageView c39xl29xm19xz09;
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
        try {
            client = Client.getInstance();
            client.addObserver(this);
        } catch (IOException e) {
            log.error("Chyba při inicializaci klienta", e);
        }
        gameEnded = false;
        cf = new CommandFactory(this);
        aktualniLobby = Main.getLobby();
        initializeImageViewMap();
        initializePlayerColor();
        setFigurkaToFront();
        setFigurkaClickHandlers();
        initializeStartPositions();
        loadDiceImages();
        firstStart();
    }
    private void initializePlayerColor() {
        if (aktualniLobby != null && aktualniLobby.getPlayers() != null) {
            barvaHrace = aktualniLobby.getPlayers().stream()
                    .filter(Objects::nonNull)
                    .filter(player -> Main.getPlayerName().equals(player.getName()))
                    .map(player -> aktualniLobby.getBoardState().getPlayerColour(player.getName()))
                    .findFirst()
                    .orElse(null);
        }
        barvaHrace = convertEnglishColourToCzech(barvaHrace);
    }
    private void initializeImageViewMap() {
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
        imageViewMap.put("c31xl21xm11xz01", c31xl21xm11xz01);
        imageViewMap.put("c32xl22xm12xz02", c32xl22xm12xz02);
        imageViewMap.put("c33xl23xm13xz03", c33xl23xm13xz03);
        imageViewMap.put("c34xl24xm14xz04", c34xl24xm14xz04);
        imageViewMap.put("c35xl25xm15xz05", c35xl25xm15xz05);
        imageViewMap.put("c36xl26xm16xz06", c36xl26xm16xz06);
        imageViewMap.put("c37xl27xm17xz07", c37xl27xm17xz07);
        imageViewMap.put("c38xl28xm18xz08", c38xl28xm18xz08);
        imageViewMap.put("c39xl29xm19xz09", c39xl29xm19xz09);
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
    }
    private void setFigurkaToFront() {
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
    }
    private void setFigurkaClickHandlers() {
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
    private void initializeStartPositions() {
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
    }
    private void loadDiceImages() {
        diceImages = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            String adresa = "/kostka/" + i + ".png";
            log.debug("Pokus o nahrání obrázku, adresa: " + adresa);
            Image obrazek = new Image(getClass().getResourceAsStream(adresa));
            log.debug("Název obrázku: " + obrazek.getUrl());
            diceImages.add(obrazek);
        }
    }
    private void handleFigurkaClick(MouseEvent event) {
        selectedFigurka = (ImageView) event.getSource();
        String zvolenaFigurka = selectedFigurka.getId();
        String barvaFigurky = zvolenaFigurka.substring(8, 9);
        if(!barvaFigurky.equalsIgnoreCase(barvaHrace)) {
            return;
        }

        chosenToken = Integer.parseInt(zvolenaFigurka.substring(7, 8)) - 1; // "figurka1C" -> "1" -> token 0
        client.send("M " + chosenToken);
    }
    public void hodKostkou(ActionEvent actionEvent) {
        log.info("Byla spuštěna metoda hoď kostkou");
        Task<Void> diceRollTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                int rolls = 5 + random.nextInt(6); // Hodit 5-10x
                for (int i = 0; i < rolls; i++) {
                    Platform.runLater(() -> kostkaImageView.setImage(diceImages.get(random.nextInt(6))));
                    Thread.sleep(100); // Mezi každým hodem počkat 100 ms
                }

                client.send("R");
                return null;
            }
        };

        log.info("Spouštím vlákno pro roll kostkou");
        new Thread(diceRollTask).start();

    }
    private ImageView getFigurka(String colour, int token) {
        log.debug("Poslal jsem informace o barvě " + colour + " s tokenem " + token);
        switch (colour.toUpperCase()) {
            case "RED":
                log.info("Vybírám z barvy ČERVENÁ");
                return switch (token) {
                    case 0 -> figurka1C;
                    case 1 -> figurka2C;
                    case 2 -> figurka3C;
                    case 3 -> figurka4C;
                    default -> null;
                };
            case "YELLOW":
                log.info("Vybírám z barvy ŽLUTÁ");
                return switch (token) {
                    case 0 -> figurka1L;
                    case 1 -> figurka2L;
                    case 2 -> figurka3L;
                    case 3 -> figurka4L;
                    default -> null;
                };
            case "BLUE":
                log.info("Vybírám z barvy MODRÁ");
                return switch (token) {
                    case 0 -> figurka1M;
                    case 1 -> figurka2M;
                    case 2 -> figurka3M;
                    case 3 -> figurka4M;
                    default -> null;
                };
            case "GREEN":
                log.info("Vybírám z barvy ZELENÁ");
                return switch (token) {
                    case 0 -> figurka1Z;
                    case 1 -> figurka2Z;
                    case 2 -> figurka3Z;
                    case 3 -> figurka4Z;
                    default -> null;
                };
            default:
                log.debug("NEBYLA ZVOLENA ŽÁDNÁ FIGURKA");
                return null;
        }
    }
    private String convertEnglishColourToCzech(String colour) {
        switch (colour.toLowerCase()) {
            case "red":
                colour = "c";
                break;
            case "yellow":
                colour = "l";
                break;
            case "blue":
                colour = "m";
                break;
            case "green":
                colour = "z";
                break;
            default:
                return null;
        }
        return colour;
    }
    private ImageView getImageView(String colour, int position) {
        ImageView obrazek;
        String key;
        colour = convertEnglishColourToCzech(colour);

        log.debug("Zjišťuji, jaký bude klíč na základě barvy " + colour + " a pozice " + position);
        if(colour.equalsIgnoreCase("z") && position < 10) {
            log.info("Barva je zelená a zároveň její pozice je nižší než 10");
            key = colour + "0" + position;
        }
        else if(position > 40 || (colour.equalsIgnoreCase("z") && position < 40)) {
            log.info("Barva není zelená, pozice je vyšší než 40");
            key = colour + position;
            // KVŮLI DOMEČKŮM, NEEXISTUJE POZICE C41X
        }
        else {
            log.info("Barva není zelená a zároveň její pozice není nižší než 10 a zároveň není vyšší než 40");
            key = colour + position + "x";
        }
        log.debug("Klíč: " + key);
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
        List<Pair<Double, Double>> pary;

        for(Player aktualniHrac : aktualniHraci) {
            aktualniToken = -1;
            for (Token token : aktualniHrac.getTokens()) {
                barvaAktualniHrac = aktualniDeska.getPlayerColour(aktualniHrac.getName());
                aktualniToken++;
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
                    pary = startPositions.get(barvaAktualniHrac);
                    if(token.getPosition() == 0) { // Pokud má být figurka na startovní pozici
                        int numOfFigurkaMinusOne = Integer.parseInt(figurka.getId().substring(7, 8)) - 1;
                        Pair<Double, Double> par = pary.get(numOfFigurkaMinusOne);
                        figurka.setLayoutX(par.getKey());
                        figurka.setLayoutY(par.getValue());
                        stopBlinkAnimation(figurka);
                    }
                    continue;
                }
                poziceNaDesce = getImageView(barvaAktualniHrac, poziceTokenu);
                if(poziceNaDesce == null) continue;

                figurka.setLayoutX(poziceNaDesce.getLayoutX() + poziceNaDesce.getFitWidth() / 4);
                figurka.setLayoutY(poziceNaDesce.getLayoutY() + poziceNaDesce.getFitHeight() / 4);
                stopBlinkAnimation(figurka);
            }
        }
    }
    public void odeslat(ActionEvent actionEvent) throws IOException {
        // Chat mezi hráči pomocí příkazu H, možné rozšíření
    }
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
        else if (arg instanceof Map) {
            Map<Integer, MovableToken> movableTokens = (Map<Integer, MovableToken>) arg;
            flashTokens(movableTokens);
        }
        else if (arg instanceof Player) {
            Player winner = (Player) arg;
            JOptionPane.showMessageDialog(null, "Vítěz je: " + winner.getName(), "Výsledek hry", JOptionPane.INFORMATION_MESSAGE);
            Platform.runLater(() -> {
                if(!gameEnded) {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/lobby.fxml"));
                    } catch (IOException e) {
                        log.error("Ocitl se problém v načítání lobby.fxml", e);
                    }

                    // Vytvoření scény
                    Scene scene = new Scene(root, 703, 980);

                    // Nastavení scény a zobrazení hlavního okna
                    primaryStage.close();
                    primaryStage.setTitle("Člověče, nezlob se! - " + Main.getPlayerName());
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    //LobbyController.primaryStage = primaryStage;
                    log.info("Spuštěna aplikace z aplikace.fxml se jménem " + Main.getPlayerName());
                    gameEnded = true;
                }
            });
            return;
        }
    }
    private void flashTokens(Map<Integer, MovableToken> movableTokens) {
        Player hracNaTahu = aktualniLobby.getBoardState().getPlayerOnTurn();
        String barvaHraceNaTahu = aktualniLobby.getBoardState().getPlayerColour(hracNaTahu.getName());

        movableTokens.forEach((key, token) -> {
            ImageView figurka = getFigurka(barvaHraceNaTahu, key);
            if (figurka != null) {
                Timeline timeline = createBlinkAnimation(figurka);
                animationMap.put(figurka, timeline); // Uložení animaci do mapy
                timeline.play();
            }
        });
    }
    private Timeline createBlinkAnimation(ImageView figurka) {
        Timeline blinkTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(figurka.opacityProperty(), 1.0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(figurka.opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(figurka.opacityProperty(), 1.0))
        );
        blinkTimeline.setCycleCount(Timeline.INDEFINITE); // Opakuje se nekonečně
        return blinkTimeline;
    }
    private void stopBlinkAnimation(ImageView figurka) {
        figurka.setOpacity(1.0); // Nastaví figurku na plnou viditelnost
        Timeline timeline = animationMap.get(figurka); // Získá animaci z mapy
        if (timeline != null) {
            timeline.stop(); // Zastaví animaci
            animationMap.remove(figurka); // Odstraní animaci z mapy
        }
    }
}
