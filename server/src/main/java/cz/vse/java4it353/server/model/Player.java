package cz.vse.java4it353.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.logic.Game;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private String name;
    @JsonIgnore
    private Socket clientSocket;
    Token[] tokens = {new Token(), new Token(), new Token(), new Token()};

    public Player(String name, Socket clientSocket) {
        this.name = name;
        this.clientSocket = clientSocket;
    }

    public Player() {
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = new Token();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Token[] getTokens() {
        return tokens;
    }
    public void moveToken(int tokenIndex, int steps, Lobby lobby) {
        int newPosition = tokens[tokenIndex].move(steps);

        Map <ColorEnum, Player> playerMap = lobby.getBoardState().getPlayerMap();
        ColorEnum colorPlayer = null;
        for (ColorEnum colorEnum : playerMap.keySet()){
            if (playerMap.get(colorEnum).equals(this)){
                colorPlayer = colorEnum;
            }
        }

        for (ColorEnum color : playerMap.keySet()) {
            checkCollision(color, lobby.getBoardState().getPlayerMap().get(color), newPosition, colorPlayer);
        }
    }

    public Map<Integer, Token> getMovableTokens(int diceValue) {
        Map<Integer, Token> movableTokens = new HashMap<>();
        for (int i = 0; i < tokens.length; i++) {
            if (canMove(tokens[i], diceValue)) {
                movableTokens.put(i, tokens[i]);
            }
        }
        return movableTokens;
    }

    private boolean canMove(Token token, int diceValue) {
        if (token.getPosition() == 0) {
            return diceValue == 6;
        } else {
            int newPosition = token.getPosition() + diceValue;
            boolean overflew = newPosition > Board.BOARD_SIZE;

            if (!overflew) {
                return false;
            }

            for (Token t : tokens) {
                if (t.getPosition() == newPosition) {
                    return false;
                }
            }

            return true;
        }
    }

    private void checkCollision(ColorEnum otherColor, Player otherPlayer, int newPosition, ColorEnum currentColor) {
        int playerOffset = Game.offset.get(currentColor);
        int otherPlayerOffset = Game.offset.get(otherColor);

        if (otherPlayer != this && otherPlayer != null) {
            for (Token otherToken : otherPlayer.getTokens()) {
                if ((otherToken.getPosition() + otherPlayerOffset) % Board.BOARD_SIZE  == (newPosition + playerOffset) % Board.BOARD_SIZE) {
                    otherToken.setPosition(0);
                }
            }
        }
    }
}
