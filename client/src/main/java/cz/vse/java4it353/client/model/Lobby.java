package cz.vse.java4it353.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lobby {
    private String name;
    private List<Player> players;
    private Map<String, Player> playerMap;
    private Board boardState;
    private boolean started;

    @JsonCreator
    public Lobby(
            @JsonProperty("name") String name,
            @JsonProperty("players") List<Player> players,
            @JsonProperty("boardState") Board boardState,
            @JsonProperty("started") boolean started
    ) {
        this.name = name;
        this.players = players != null ? players : new ArrayList<>();
        this.boardState = boardState != null ? boardState : new Board();
        this.started = started;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoardState() {
        return boardState;
    }

    public void setBoardState(Board boardState) {
        this.boardState = boardState;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    public int getNumOfPlayers() {
        int i = 0;
        for(Player player: players) {
            if(player != null)
                i++;
        }
        return i;
    }
    public boolean isPlayerInLobby(String name) {
        for (Player player: players) {
            if(player != null && player.getName() == name) {
                return true;
            }
        }
        return false;
    }
}