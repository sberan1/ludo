package cz.vse.java4it353.client.model;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<String, Player> playerMap;
    private String playerOnTurn;
    private int diceValue;

    public Board() {
        this.playerMap = new HashMap<>();
    }
}