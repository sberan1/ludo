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

/**
 * Player class representing a player in the game
 */
public class Player {
    private String uuid;
    private String name;
    @JsonIgnore
    private Socket clientSocket;
    Token[] tokens = {new Token(), new Token(), new Token(), new Token()};
    private static final Logger log = LoggerFactory.getLogger(Player.class);

    /**
     * Constructor
     * @param name name of the player
     * @param clientSocket socket of the player
     */
    public Player(String name, Socket clientSocket) {
        this.name = name;
        this.clientSocket = clientSocket;
        uuid = UUID.randomUUID().toString();
    }

    /**
     * Constructor
     */
    public Player() {
        uuid = UUID.randomUUID().toString();
    }

    /**
     * Get client socket
     * @return client socket
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    /**
     * Set client socket
     * @param clientSocket client socket
     */
    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = new Token();
        }
    }

    /**
     * Get UUID
     * @return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Set UUID
     * @param uuid UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param name name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get tokens
     * @return tokens
     */
    public Token[] getTokens() {
        return tokens;
    }

    /**
     * Moving tokens on the board
     *
     * @param tokenIndex index of the token you want to move
     * @param steps number of steps you want to move the token
     * @param lobby lobby where the game is played
     * @throws ForbiddenMoveException if the move is forbidden for any reason
     */
    public void moveToken(int tokenIndex, int steps, Lobby lobby) throws ForbiddenMoveException {
            Token token = tokens[tokenIndex];
            int newPosition = 0;

          if (canMove(token, steps)) {
                newPosition = token.move(steps);
                log.info("Player " + name + " moved token " + tokenIndex + " to position " + newPosition);
          } else {
                log.info("Player " + name + " couldn't move token " + tokenIndex);
                throw new ForbiddenMoveException("Player " + name + " couldn't move token " + tokenIndex);
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

    /**
     * Get movable tokens
     * @param diceValue value of the dice
     * @return map of movable tokens
     */
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
            for (Token t : tokens) {
                if (t.getPosition() == 1) {
                    return false;
                }
            }
            return diceValue == 6;
        } else {
            int newPosition = token.getPosition() + diceValue;
            boolean overflew = newPosition > Board.BOARD_SIZE;

            if (overflew) {
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

    protected void checkCollision(int otherPlayerOffset, Player otherPlayer, int newPosition, int playerOffset) {

        if (otherPlayer != this && otherPlayer != null) {
            for (Token otherToken : otherPlayer.getTokens()) {
                //log.debug( " Absolute pozice druheho " + (otherToken.getPosition() + otherPlayerOffset) % (Board.BOARD_SIZE - 4));
                //log.debug( " Absolute pozice prveho " + (newPosition + playerOffset) % (Board.BOARD_SIZE - 4));
                if (((otherToken.getPosition() + otherPlayerOffset) % (Board.BOARD_SIZE - 4)
                        == (newPosition + playerOffset) % (Board.BOARD_SIZE - 4))
                        && otherToken.getPosition() != 0
                        && otherToken.getPosition() < Board.BOARD_SIZE - 4) {
                    otherToken.setPosition(0);
                }
            }
        }
    }
}
