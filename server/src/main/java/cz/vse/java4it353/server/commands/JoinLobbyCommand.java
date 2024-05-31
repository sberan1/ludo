package cz.vse.java4it353.server.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;

import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Command for joining a lobby
 *
 * @author sberan
 */
public class JoinLobbyCommand implements ICommand {
    private Socket clientSocket;
    private List<Socket> clientSockets;
    Logger logger = Logger.getLogger(JoinLobbyCommand.class.getName());
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor
     * @param clientSocket socket of current player
     * @param clientSockets list of all client sockets
     */
    public JoinLobbyCommand(Socket clientSocket, List<Socket> clientSockets) {
        this.clientSocket = clientSocket;
        this.clientSockets = clientSockets;
    }

    @Override
    public String execute(String data) throws Exception {
        if (data == null) {
            logger.warning("No data provided");
            throw new Exception("No data provided");
        }
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
        game.notifyPlayers("L " + game.JSONLobbies(), clientSockets);
        return "J " + mapper.writeValueAsString(lobby);
    }
}
