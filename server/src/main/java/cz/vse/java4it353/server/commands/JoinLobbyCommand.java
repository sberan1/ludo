package cz.vse.java4it353.server.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;

import java.net.Socket;
import java.util.logging.Logger;

public class JoinLobbyCommand implements ICommand {
    private Socket clientSocket;
    Logger logger = Logger.getLogger(JoinLobbyCommand.class.getName());
    ObjectMapper mapper = new ObjectMapper();

    public JoinLobbyCommand(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public String execute(String data) throws Exception {
        Game game = Game.getInstance();
        Lobby lobby = game.getLobby(data);

        if (lobby == null) {
            logger.warning("Lobby not found");
            return "error while joining lobby - lobby not found";
        }

        Player player = game.getPlayerBySocket(clientSocket);

        if (player == null) {
            logger.warning("Player not logged in");
            return "error while joining lobby - player not logged in";
        }

        lobby.addPlayer(player);
        return "J " + mapper.writeValueAsString(lobby);
    }
}
