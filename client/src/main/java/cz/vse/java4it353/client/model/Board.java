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

    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(Map<String, Player> playerMap) {
        this.playerMap = playerMap;
    }

    public String getPlayerOnTurn() {
        return playerOnTurn;
    }

    public void setPlayerOnTurn(String playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }
}