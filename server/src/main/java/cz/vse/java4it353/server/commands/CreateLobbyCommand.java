package cz.vse.java4it353.server.commands;

import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateLobbyCommand implements ICommand{
    List<Socket> clientSockets;
    Socket clientSocket;
    Logger logger = Logger.getLogger(CreateLobbyCommand.class.getName());
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
            logger.log(Level.WARNING, "player not found");
            return "error while creating lobby";
        }
        String lobbies = game.JSONLobbies();
        clientSockets.forEach(socket -> {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(lobbies);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "error while sending lobby info to " + socket + " " + e.getMessage());
            }
        });
        return lobbies;
    }
}
