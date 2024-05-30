package cz.vse.java4it353.server.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.exception.IncorrectlyDefinedArgumentException;

public class Board {
    public Map<ColorEnum, Player> getPlayerMap() {
        return playerMap;
    }

    public static final int BOARD_SIZE = 44;
    private int DiceValue;
    private final Map<ColorEnum, Player> playerMap = new HashMap<>();
    private Player playerOnTurn;
    @JsonIgnore
    private boolean hasPlayerRolled = false;

    public boolean hasPlayerRolled() {
        return hasPlayerRolled;
    }

    public void setHasPlayerRolled(boolean hasPlayerRolled) {
        this.hasPlayerRolled = hasPlayerRolled;
    }

    public void setPlayer(Player player, ColorEnum color) throws IncorrectlyDefinedArgumentException {
        playerMap.values().remove(player);

        if (playerMap.get(color) != null) {
            throw new IncorrectlyDefinedArgumentException(color + " player is already set");
        }
        playerMap.put(color, player);
    }
    public int rollDice() {
        hasPlayerRolled = true;
        setDiceValue((int) (Math.random() * 6) + 1);
        // NA TESTOVÁNÍ setDiceValue(6);
        return getDiceValue();
    }

    public int getDiceValue() {
        return DiceValue;
    }

    public void setDiceValue(int diceValue) {
        DiceValue = diceValue;
    }

    public Player getPlayerOnTurn() {
        return playerOnTurn;
    }

    public void setPlayerOnTurn(Player playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
    }

    public void nextPlayerOnTurn() {
        hasPlayerRolled = false;
        ColorEnum currentColor = null;
        if (playerOnTurn != null) {
            for (Map.Entry<ColorEnum, Player> entry : playerMap.entrySet()) {
                if (entry.getValue().equals(playerOnTurn)) {
                    currentColor = entry.getKey();
                    break;
                }
            }
        }

        ColorEnum[] colors = ColorEnum.values();
        for (int i = 0; i < colors.length; i++) {
            if (currentColor == colors[i]) {
                for (int j = 1; j <= colors.length; j++) {
                    ColorEnum nextColor = colors[(i + j) % colors.length];
                    if (playerMap.get(nextColor) != null) {
                        playerOnTurn = playerMap.get(nextColor);
                        return;
                    }
                }
            }
        }

        for (ColorEnum color : colors) {
            if (playerMap.get(color) != null) {
                playerOnTurn = playerMap.get(color);
                return;
            }
        }
    }

}
