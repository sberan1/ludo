package cz.vse.java4it353.server.model;


public class Board {
    private static final int BOARD_SIZE = 44;
    private int DiceValue;
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
