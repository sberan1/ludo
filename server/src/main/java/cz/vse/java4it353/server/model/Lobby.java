package cz.vse.java4it353.server.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.logic.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Class representing a lobby in the game
 *
 * @author sberan
 */
public class Lobby {
    private String name;
    private boolean isStarted = false;
    ObjectMapper mapper = new ObjectMapper();
    /**
     * Maximum number of players in the lobby
     */
    public static final int MAX_PLAYERS = 4;
    /**
     * Minimum number of players in the lobby
     */
    public static final int MIN_PLAYERS = 2;
    private Player[] players = new Player[MAX_PLAYERS];
    private static final Logger logger = LoggerFactory.getLogger(Lobby.class);
    private Board boardState;

    /**
     * Constructor
     * @param name name of the lobby
     */
    public Lobby(String name) {
        this.name = name;
        this.boardState = new Board();
    }

    /**
     * Get name of the lobby
     * @return name of the lobby
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of the lobby
     * @param name name of the lobby
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get players in the lobby
     * @return players in the lobby
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Set players in the lobby
     * @param players players in the lobby
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Add player to the lobby
     * @param player player to be added
     * @throws IllegalArgumentException if the player is already in the lobby
     */
    public void addPlayer(Player player) throws IllegalArgumentException{
       boolean isPlayerAlreadyInLobby = Arrays.stream(players).anyMatch(p -> p == player);
       if (isPlayerAlreadyInLobby) {
           logger.warn("Player is already in the lobby");
           throw new IllegalArgumentException("Player is already in the lobby");
       }
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }

    /**
     * Tells you if the lobby is started
     * @return true if the lobby is started, false otherwise
     */
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * Set the lobby as started
     * @param started true if the lobby is started, false otherwise
     * @throws IllegalStateException if the lobby is not started for not enough players or if the player does not have a set color
     */
    public void setStarted(boolean started) throws IllegalStateException{
        int playerCount = (int) java.util.Arrays.stream(this.getPlayers()).filter(java.util.Objects::nonNull).count();
        if (started && playerCount >= MIN_PLAYERS) {
            for (Player player : this.getPlayers()) {
                if (player != null && !this.boardState.getPlayerMap().containsValue(player)) {
                    logger.warn("Player does not have a set color");
                    throw new IllegalStateException("Player does not have a set color");
                }
            }
            this.boardState.nextPlayerOnTurn();
            isStarted = true;
        }
        else if (!started) {
            isStarted = false;
        }
        else {
            isStarted = false;
            logger.warn("Not enough players to start the game");
            throw new IllegalStateException("Not enough players to start the game");
        }
    }

    /**
     * Sends message to all players in the lobby
     * @param message message to be sent
     */
    public void sendMessageToAllPlayers(String message) {
        for (Player player : players) {
            try {
                if (player == null) {
                    logger.info("Player is null, not enough players in the lobby");
                    continue;
                }
                PrintWriter out = new PrintWriter(player.getClientSocket().getOutputStream(), true);
                logger.info("Sending message to player: " + player.getName() + " message: " + message);
                out.println(message);
            } catch (IOException e) {
                System.out.println("Error sending message to player: " + e.getMessage());
            }
        }
    }

    /**
     * Get board state
     * @return board state
     */
    public Board getBoardState() {
        return boardState;
    }

    public void removePlayer(Player player){
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                players[i] = null;
                break;
            }
        }
    }

    public void checkIfgameEnded() throws JsonProcessingException {
        int playerCount = (int) java.util.Arrays.stream(this.getPlayers()).filter(java.util.Objects::nonNull).count();
        if (playerCount <= 1) {
            for(Player player : this.getPlayers()){
                if(player != null){
                    this.sendMessageToAllPlayers("W " + mapper.writeValueAsString(player));
                    Game.getInstance().removeLobby(this);
                }
            }
        }

    }

    /**
     * Set board state
     * @param boardState board state
     */
    public void setBoardState(Board boardState) {
        this.boardState = boardState;
    }

}
