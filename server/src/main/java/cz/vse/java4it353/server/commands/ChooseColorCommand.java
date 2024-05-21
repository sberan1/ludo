package cz.vse.java4it353.server.commands;

import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.Socket;
import java.util.Arrays;

public class ChooseColorCommand implements ICommand {

    Socket clientSocket;
    private final Logger logger = LoggerFactory.getLogger(ChooseColorCommand.class);
    ObjectMapper mapper = new ObjectMapper();


    public ChooseColorCommand(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public String execute(String data) throws Exception {
        if (data == null) {
            logger.warn("No data provided");
            throw new Exception("No data provided");
        }

        try {
            ColorEnum color = ColorEnum.valueOf(data.toUpperCase());
            Game game = Game.getInstance();
            Lobby lobby = game.listLobbies().stream()
                    .filter(lobbyLocal -> Arrays.stream(lobbyLocal.getPlayers())
                    .anyMatch(player -> player.getClientSocket() == clientSocket))
                    .findFirst()
                    .orElse(null);
            if (lobby == null) {
                logger.warn("Player not found in any lobby");
                throw new Exception("Player not found in any lobby");
            }

            Player player = Arrays.stream(lobby.getPlayers())
                    .filter(pl -> pl.getClientSocket() == clientSocket)
                    .findFirst()
                    .orElse(null);

            if (player == null) {
                logger.warn("Player not found in lobby");
                throw new Exception("Player not found in lobby");
            }

            lobby.getBoardState().setPlayer(player, color);
            return "J " + mapper.writeValueAsString(lobby);

        }
        catch (IllegalArgumentException e) {
            logger.warn("Invalid color");
            throw new Exception("Invalid color");
        }

    }

}
