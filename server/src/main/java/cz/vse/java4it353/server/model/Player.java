package cz.vse.java4it353.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.vse.java4it353.server.enums.ColorEnum;

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

        for (ColorEnum color : lobby.getBoardState().getPlayerMap().keySet()) {
            switch (color) {
                case RED:
                    checkCollision(lobby.getBoardState().getPlayerMap().get(ColorEnum.RED), newPosition);
                    break;
                case YELLOW:
                    checkCollision(lobby.getBoardState().getPlayerMap().get(ColorEnum.GREEN), newPosition-10);
                    break;
                case BLUE:
                    checkCollision(lobby.getBoardState().getPlayerMap().get(ColorEnum.BLUE), newPosition-20);
                    break;
                case GREEN:
                    checkCollision(lobby.getBoardState().getPlayerMap().get(ColorEnum.YELLOW), newPosition-30);
                    break;
            }
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

    private void checkCollision(Player otherPlayer, int newPosition) {
        if (otherPlayer != this && otherPlayer != null) {
            for (Token otherToken : otherPlayer.getTokens()) {
                if (otherToken.getPosition() == newPosition) {
                    otherToken.setPosition(0);
                }
            }
        }
    }
}
