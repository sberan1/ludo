package cz.vse.java4it353.server.commands;

import cz.vse.java4it353.server.model.Player;

import java.io.PrintWriter;
import java.util.List;
import cz.vse.java4it353.server.logic.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.Socket;

/**
 * Takes name of the player and creates a new player with that name
 *
 */
public class LoginCommand implements ICommand {
    List<Socket> clientSockets;
    private Socket clientSocket;
    private static final Logger logger = LoggerFactory.getLogger(LoginCommand.class);

    public LoginCommand(Socket clientSocket, List<Socket> clientSockets) {
        this.clientSocket = clientSocket;
        this.clientSockets = clientSockets;
    }

    /**
     * Creates a new player with the given name
     *
     * @param data name of the player
     * @return list of lobbies
     */
    @Override
    public String execute(String data) {
        Player player = new Player();
        player.setName(data);
        player.setClientSocket(clientSocket);
        Game game = Game.getInstance();
        game.addPlayer(player);
        logger.info("Player " + player.getName() + " has logged in");
        String lobbies = game.JSONLobbies();
        game.notifyPlayers(lobbies, clientSockets);
        return "L " + lobbies;
    }
}
