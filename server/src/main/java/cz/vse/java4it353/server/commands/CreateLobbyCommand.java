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
        Lobby lobby = new Lobby(data);
        Game game = Game.getInstance();
        game.addLobby(lobby);
        Player matchingPlayer = null;
        for (Player player : game.listPlayers()) {
            if (player.getClientSocket().equals(clientSocket)) {
                matchingPlayer = player;
                break;
            }
        }
        if (matchingPlayer != null) {
            lobby.addPlayer(matchingPlayer);
        }
        else {
            logger.warn("player not found");
            return "error while creating lobby";
        }
        String lobbies = game.JSONLobbies();
        //game.notifyPlayers(lobbies, clientSockets);
        return "J " + mapper.writeValueAsString(lobby);
    }
}
