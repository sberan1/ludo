package cz.vse.java4it353.server.commands;

import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


public class CreateLobbyCommand implements ICommand{
    List<Socket> clientSockets;
    Socket clientSocket;
    private static final Logger logger = LoggerFactory.getLogger(CreateLobbyCommand.class);
    public CreateLobbyCommand(Socket clientSocket, List<Socket> clientSockets){
        this.clientSocket = clientSocket;
        this.clientSockets = clientSockets;
    }
    @Override
    public String execute(String data) {
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
        clientSockets.forEach(socket -> {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(lobbies);
            } catch (Exception e) {
                logger.warn("error while sending lobby info to " + socket + " " + e.getMessage());
            }
        });
        return lobbies;
    }
}
