package cz.vse.java4it353.client.model;

import cz.vse.java4it353.client.enums.ColorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Třída pro definici hrací desky
 */
public class Board {
    private static final Logger logger = LoggerFactory.getLogger(Board.class);
    private Map<ColorEnum, Player> playerMap;
    private Player playerOnTurn;
    private int diceValue;

    /**
     * Konstruktor třídy
     */
    public Board() {
        this.playerMap = new HashMap<>();
    }

    public Map<ColorEnum, Player> getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(Map<ColorEnum, Player> playerMap) {
        this.playerMap = playerMap;
    }

    public Player getPlayerOnTurn() {
        return playerOnTurn;
    }

    public void setPlayerOnTurn(Player playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }
    public String getPlayerColour(String name) {
        ColorEnum currentColor = null;
        for (Map.Entry<ColorEnum, Player> entry : playerMap.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                currentColor = entry.getKey();
                break;
            }
        }
        return String.valueOf(currentColor);
    }
}