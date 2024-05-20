package cz.vse.java4it353.server.logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game {
    private static Game instance = null;
    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    Map<String, Lobby> lobbies;
    Map<String, Player> players;
    ObjectMapper mapper = new ObjectMapper();


    private Game() {
        lobbies = new HashMap<String, Lobby>();
        players = new HashMap<String, Player>();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void addLobby(Lobby lobby) {
        lobbies.put(lobby.getName(), lobby);
    }

    public void removeLobby(String name) {
        lobbies.remove(name);
    }

    public List<Lobby> listLobbies()  {
        return List.copyOf(lobbies.values());
    }

    public List<Player> listPlayers() {
        return List.copyOf(players.values());
    }

    public void addPlayer(Player player) {
        players.put(player.getName(), player);
    }

    public Lobby getLobby(String name) {
        return lobbies.get(name);
    }

    public Player getPlayer(String name) {
        return players.get(name);
    }

    public Player getPlayerBySocket(Socket socket) {
        for (Player player : players.values()) {
            if (player.getClientSocket().equals(socket)) {
                return player;
            }
        }
        return null;
    }

    public Lobby getLobbyWithPlayer(Socket clientSocket) {
        for (Lobby lobby : lobbies.values()) {
            for (Player player : lobby.getPlayers()) {
                if (player != null && player.getClientSocket() == clientSocket) {
                    return lobby;
                }
            }
        }
        return null;
    }

    public String JSONLobbies() {
        try {
            return mapper.writeValueAsString(lobbies);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return "error while serializing lobbies to JSON";
        }
    }
}
