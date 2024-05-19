package cz.vse.java4it353.server.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Lobby {
    private String name;
    private boolean isStarted = false;
    public static final int MAX_PLAYERS = 4;
    public static final int MIN_PLAYERS = 2;
    private Player[] players = new Player[MAX_PLAYERS];
    private static final Logger logger = LoggerFactory.getLogger(Lobby.class);
    private Board boardState;

    public Lobby(String name) {
        this.name = name;
        this.boardState = new Board();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

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

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) throws IllegalStateException{
        int playerCount = (int) java.util.Arrays.stream(this.getPlayers()).filter(java.util.Objects::nonNull).count();
        if (started && playerCount >= MIN_PLAYERS) {
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

    public Board getBoardState() {
        return boardState;
    }

    public void setBoardState(Board boardState) {
        this.boardState = boardState;
    }

}
