package cz.vse.java4it353.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.vse.java4it353.client.controllers.HomeController;
import cz.vse.java4it353.client.enums.ColorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Třída pro definici lobby
 */
public class Lobby {
    private static final Logger log = LoggerFactory.getLogger(Lobby.class);
    private String name;
    private List<Player> players;
    private Map<String, Player> playerMap;
    private Board boardState;
    private boolean started;

    /**
     * Konstruktor třídy
     * @param name Název lobby
     * @param players Všichni hráči
     * @param boardState Stav hrací desky
     * @param started Začala hra?
     */
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

    /**
     * Konstruktor třídy
     */
    public Lobby() {}
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
        log.debug("Zachycené informace o desce, začíná výpis");
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
            if(player != null && player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}