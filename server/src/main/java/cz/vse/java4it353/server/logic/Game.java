package cz.vse.java4it353.server.logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Game class containing all lobbies and players and logic of the game
 *
 * @author sberan
 */
public class Game {
    private static Game instance = null;
    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    Map<String, Lobby> lobbies;
    Map<String, Player> players;
    public static Map<ColorEnum, Integer> offset = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();


    private Game() {
        lobbies = new HashMap<String, Lobby>();
        players = new HashMap<String, Player>();
    }

    /**
     * Returns an instance of a class Game - singleton pattern
     *
     * @return instance
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
            offset.put(ColorEnum.RED, 0);
            offset.put(ColorEnum.YELLOW, 10);
            offset.put(ColorEnum.GREEN, 30);
            offset.put(ColorEnum.BLUE, 20);
        }
        return instance;
    }

    /**
     * Adds a lobby to the game
     *
     * @param lobby lobby to be added
     */

    public void addLobby(Lobby lobby) {
        lobbies.put(lobby.getName(), lobby);
    }

    /**
     * Removes a lobby from the game
     *
     * @param name name of the lobby to be removed
     */
    public void removeLobby(String name) {
        lobbies.remove(name);
    }

    /**
     * Lists all lobbies
     *
     * @return list of lobbies
     */
    public List<Lobby> listLobbies()  {
        return List.copyOf(lobbies.values());
    }

    /**
     * Lists all players
     *
     * @return list of players
     */
    public List<Player> listPlayers() {
        return List.copyOf(players.values());
    }

    /**
     * Adds a player to the game
     *
     * @param player player to be added
     */
    public void addPlayer(Player player) {
        players.put(player.getName(), player);
    }

    /**
     * Removes a player from the game
     *
     * @param name name of the player to be removed
     * @return removed player
     */
    public Lobby getLobby(String name) {
        return lobbies.get(name);
    }

    /**
     * Returns a player by name
     *
     * @param name name of the player
     * @return player
     */
    public Player getPlayer(String name) {
        return players.get(name);
    }

    /**
     * Returns a player by socket
     *
     * @param socket socket of the player
     * @return player
     */
    public Player getPlayerBySocket(Socket socket) {
        for (Player player : players.values()) {
            if (player.getClientSocket().equals(socket)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Returns a lobby with a player
     *
     * @param clientSocket socket of the player
     * @return lobby
     */
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

    /**
     * Serializes lobbies to JSON
     *
     * @return JSON representation of lobbies
     */
    public String JSONLobbies() {
        try {
            return mapper.writeValueAsString(lobbies);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return "error while serializing lobbies to JSON";
        }
    }

    /**
     * Notifies all players in the game
     *
     * @param data data to be sent
     * @param clientSockets list of all client sockets
     */
    public void notifyPlayers(String data, List<Socket> clientSockets) {
        clientSockets.forEach(socket -> {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(data);
            } catch (Exception e) {
                logger.warn("error while sending lobby info to " + socket + " " + e.getMessage());
            }
        });
    }
}
