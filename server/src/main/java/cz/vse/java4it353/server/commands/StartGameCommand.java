package cz.vse.java4it353.server.commands;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.Arrays;

public class StartGameCommand implements ICommand{
    private Socket clientSocket;
    private static final Logger logger = LoggerFactory.getLogger(StartGameCommand.class);
    ObjectMapper mapper = new ObjectMapper();

    public StartGameCommand(Socket clientSocket) {
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
                return mapper.writeValueAsString(lobby);
            }
        }
        logger.warn("Player not found in lobby");
        throw new Exception("Player not found in lobby");
    }
}
