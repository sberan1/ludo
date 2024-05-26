package cz.vse.java4it353.client.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private String name;
    private List<Player> players;
    private Board boardState;
    private boolean started;

    public Lobby() {
        this.players = new ArrayList<>();
        this.boardState = new Board();
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
}