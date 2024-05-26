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
}