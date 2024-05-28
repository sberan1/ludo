package cz.vse.java4it353.server.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.List;


public class CreateLobbyCommand implements ICommand{
    List<Socket> clientSockets;
    Socket clientSocket;
    ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(CreateLobbyCommand.class);
    public CreateLobbyCommand(Socket clientSocket, List<Socket> clientSockets){
        this.clientSocket = clientSocket;
        this.clientSockets = clientSockets;
    }
    @Override
    public String execute(String data) throws Exception {
        if (data == null) {
            logger.error("No data provided");
            throw new Exception("No data provided");
        }
        Game game = Game.getInstance();
        if (game.getLobbyWithPlayer(clientSocket) != null) {
            logger.warn("Player already in lobby");
            return "error while creating lobby - player already in lobby";
        }
        Lobby lobby = new Lobby(data);
        game.addLobby(lobby);
        logger.info("Lobby " + lobby.getName() + " has been created and added to the game");
        Player matchingPlayer = null;
        for (Player player : game.listPlayers()) {
            if (player.getClientSocket().equals(clientSocket)) {
                matchingPlayer = player;
                logger.info(player.getName() + "with address" + clientSocket.getInetAddress().getHostAddress() +  " has been found in the game");
                break;
            }
        }
        if (matchingPlayer != null) {
            lobby.addPlayer(matchingPlayer);
            logger.info("Player " + matchingPlayer.getName() + " has joined the lobby");
        }
        else {
            logger.warn("player not found");
            return "error while creating lobby";
        }
        String lobbies = game.JSONLobbies();
        game.notifyPlayers("L " + lobbies, clientSockets);
        return "J " + mapper.writeValueAsString(lobby);
    }
}
