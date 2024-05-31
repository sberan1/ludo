package cz.vse.java4it353.server.commands;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.List;

/**
 * Command for starting the game
 *
 * @author sberan
 */
public class StartGameCommand implements ICommand{
    private final Socket clientSocket;
    private static final Logger logger = LoggerFactory.getLogger(StartGameCommand.class);
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor
     * @param clientSocket socket of current player
     * @param clientSockets list of all client sockets
     */
    public StartGameCommand(Socket clientSocket, List<Socket> clientSockets) {
        this.clientSocket = clientSocket;
    }

    @Override
    public String execute(String data) throws Exception {
        Game game = Game.getInstance();
        Lobby lobby = game.getLobby(data);
        if (lobby == null) {
            logger.warn("Lobby not found");
            throw new Exception("Lobby not found");
        }
        Player[] players = lobby.getPlayers();
        for (Player player : players) {
            if (player == null) {
                continue;
            }
            if (player.getClientSocket() == clientSocket) {
                lobby.setStarted(true);
                String result = mapper.writeValueAsString(lobby.getBoardState());
                lobby.sendMessageToAllPlayers("B " + result);
                return "B " + result;
            }
        }
        logger.warn("Player not found in lobby");
        throw new Exception("Player not found in lobby");
    }
}
