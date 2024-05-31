package cz.vse.java4it353.server.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.exception.IncorrectlyDefinedArgumentException;

/**
 * Board class representing the board of the game
 *
 * @author sberan
 */
public class Board {
    /**
     * Size of the board
     */
    public static final int BOARD_SIZE = 44;
    private int DiceValue;
    private final Map<ColorEnum, Player> playerMap = new HashMap<>();
    private Player playerOnTurn;
    @JsonIgnore
    private boolean hasPlayerRolled = false;

    /**
     * Information if the current player on turn has rolled the dice so he doesn't play before rolling with the old dice number
     *
     * @return true if the player has already rolled the dice in his turn, false otherwise
     */
    public boolean hasPlayerRolled() {
        return hasPlayerRolled;
    }

    /**
     * Setter for hasPlayerRolled
     *
     * @param hasPlayerRolled true if the player has already rolled the dice in his turn, false otherwise
     */
    public void setHasPlayerRolled(boolean hasPlayerRolled) {
        this.hasPlayerRolled = hasPlayerRolled;
    }

    /**
     * Sets the player to the board
     *
     * @param player player to be set
     * @param color color of the player
     * @throws IncorrectlyDefinedArgumentException if the player is already set
     */
    public void setPlayer(Player player, ColorEnum color) throws IncorrectlyDefinedArgumentException {
        playerMap.values().remove(player);

        if (playerMap.get(color) != null) {
            throw new IncorrectlyDefinedArgumentException(color + " player is already set");
        }
        playerMap.put(color, player);
    }

    /**
     * Rolls the dice
     *
     * @return value of the dice
     */
    public int rollDice() {
        hasPlayerRolled = true;
        setDiceValue((int) (Math.random() * 6) + 1);
        //setDiceValue(6);
        return getDiceValue();
    }

    /**
     * Returns the current value of the dice
     * @return current value of the dice
     */
    public int getDiceValue() {
        return DiceValue;
    }

    /**
     * Sets the value of the dice
     * @param diceValue value of the dice
     */
    public void setDiceValue(int diceValue) {
        DiceValue = diceValue;
    }

    /**
     * Returns the player on turn
     * @return player on turn
     */
    public Player getPlayerOnTurn() {
        return playerOnTurn;
    }

    /**
     * Sets the player on turn
     * @param playerOnTurn player on turn
     */
    public void setPlayerOnTurn(Player playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
    }

    /**
     * Returns the next player on turn
     */
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

    /**
     * Checks if the game is finished
     *
     * @return player who has won the game
     */
    public Player checkGameFinished() {
        for (Player player : playerMap.values()) {
            int[] lastFourPlaces = new int[4];
            for (Token token : player.getTokens()) {
                int position = token.getPosition();
                if (position >= BOARD_SIZE - 3) {
                    if (lastFourPlaces[position - (BOARD_SIZE - 3)] != 0) {
                        return null;
                    }
                    lastFourPlaces[position - (BOARD_SIZE - 3)] = 1;
                } else {
                    return null; // Token is not in the last four places
                }
            }
            if (Arrays.stream(lastFourPlaces).sum() == 4) {
                return player; // All tokens are in the last four places and no two tokens are on the same place
            }
        }
        return null;
    }

    /**
     * Returns the player map - with colors as keys and players as values
     * @return player map
     */
    public Map<ColorEnum, Player> getPlayerMap() {
        return playerMap;
    }
}
