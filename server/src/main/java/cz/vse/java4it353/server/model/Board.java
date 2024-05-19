package cz.vse.java4it353.server.model;

import java.util.HashMap;
import java.util.Map;
import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.exception.IncorrectlyDefinedArgumentException;

public class Board {
    private static final int BOARD_SIZE = 44;
    private int DiceValue;
    private final Map<ColorEnum, Player> playerMap = new HashMap<>();

    public void setPlayer(Player player, ColorEnum color) throws IncorrectlyDefinedArgumentException {
        playerMap.values().remove(player);

        if (playerMap.get(color) != null) {
            throw new IncorrectlyDefinedArgumentException(color + " player is already set");
        }
        playerMap.put(color, player);
    }

    private Player playerOnTurn;

    public int rollDice() {
        return (int) (Math.random() * 6) + 1;
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
}
