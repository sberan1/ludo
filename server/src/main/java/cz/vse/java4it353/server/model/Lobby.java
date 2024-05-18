package cz.vse.java4it353.server.model;

import cz.vse.java4it353.server.model.Player;

public class Lobby {
    String name;
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 2;
    private Player[] players = new Player[MAX_PLAYERS];
    private Board board;

    public Lobby(String name) {
        this.name = name;
        this.board = new Board();
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
    public void addPlayer(Player player) {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }
}
