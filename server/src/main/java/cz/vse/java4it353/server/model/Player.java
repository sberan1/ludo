package cz.vse.java4it353.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.exception.ForbiddenMoveException;
import cz.vse.java4it353.server.logic.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Player {
    private String uuid;
    private String name;
    @JsonIgnore
    private Socket clientSocket;
    Token[] tokens = {new Token(), new Token(), new Token(), new Token()};
    private static final Logger log = LoggerFactory.getLogger(Player.class);

    public Player(String name, Socket clientSocket) {
        this.name = name;
        this.clientSocket = clientSocket;
        uuid = UUID.randomUUID().toString();
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
    public void moveToken(int tokenIndex, int steps, Lobby lobby) throws ForbiddenMoveException {
        int newPosition = 0;
        if (tokenIndex == 0){
            if (steps == 6){
                newPosition = tokens[tokenIndex].move(1);
                log.info("Player " + name + " rolled 6, token " + tokenIndex + " put on board");
            }
            else {
                log.info("Player didn't roll 6, token " + tokenIndex + " couldn't be put on board");
            }
        }
        else {
            if (canMove(tokens[tokenIndex], steps)){
                newPosition = tokens[tokenIndex].move(steps);
                log.info("Player " + name + " moved token " + tokenIndex + " to position " + newPosition);

            }
            else{
                log.info("Player " + name + " couldn't move token " + tokenIndex + " to position " + newPosition + " because he already has a token there or because it overflows the board");
                throw new ForbiddenMoveException("Player " + name + " couldn't move token " + tokenIndex + " to position " + newPosition + " because he already has a token there or because it overflows the board");
            }
        }

        Map <ColorEnum, Player> playerMap = lobby.getBoardState().getPlayerMap();
        ColorEnum colorPlayer = null;
        for (ColorEnum colorEnum : playerMap.keySet()){
            if (playerMap.get(colorEnum).equals(this)){
                colorPlayer = colorEnum;
            }
        }
        int offset = Game.offset.get(colorPlayer);

        for (ColorEnum color : playerMap.keySet()) {
            checkCollision(Game.offset.get(color), lobby.getBoardState().getPlayerMap().get(color), newPosition, offset);
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

            if (overflew) {
                return false;
            }

            for (Token t : tokens) {
                if(t.equals(token)){
                    continue;
                }
                if (t.getPosition() == newPosition) {
                    return false;
                }
            }

            return true;
        }
    }

    private void checkCollision(int otherPlayerOffset, Player otherPlayer, int newPosition, int playerOffset) {
        if (otherPlayer != this && otherPlayer != null) {
            for (Token otherToken : otherPlayer.getTokens()) {
                if ((otherToken.getPosition() + otherPlayerOffset) % Board.BOARD_SIZE - 4
                        == (newPosition + playerOffset) % Board.BOARD_SIZE - 4
                        && otherToken.getPosition() != 0
                        && otherToken.getPosition() < Board.BOARD_SIZE - 4) {
                    otherToken.setPosition(0);
                }
            }
        }
    }
}
