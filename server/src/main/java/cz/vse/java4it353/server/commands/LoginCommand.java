package cz.vse.java4it353.server.commands;

import cz.vse.java4it353.server.model.Player;
import cz.vse.java4it353.server.logic.Game;


import java.net.Socket;
import java.util.logging.Logger;

/**
 * Takes name of the player and creates a new player with that name
 *
 */
public class LoginCommand implements ICommand {
    private Socket clientSocket;
    Logger logger = Logger.getLogger(LoginCommand.class.getName());

    public LoginCommand(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
        return game.JSONLobbies();
    }
}
