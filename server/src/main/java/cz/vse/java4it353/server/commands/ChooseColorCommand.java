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
import java.util.List;

public class ChooseColorCommand implements ICommand {

    Socket clientSocket;
    private List<Socket> clientSockets;
    private final Logger logger = LoggerFactory.getLogger(ChooseColorCommand.class);
    ObjectMapper mapper = new ObjectMapper();


    public ChooseColorCommand(Socket clientSocket, List<Socket> clientSockets) {
        this.clientSocket = clientSocket;
        this.clientSockets = clientSockets;
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
            Lobby lobby = game.getLobbyWithPlayer(clientSocket);
            if (lobby == null) {
                logger.warn("Player not found in any lobby");
                throw new Exception("Player not found in any lobby");
            }
            logger.info("Lobby " + lobby.getName() + " found.");


            Player player = game.getPlayerBySocket(clientSocket);
            if (player == null) {
                logger.warn("Player not found in lobby");
                throw new Exception("Player not found in lobby");
            }
            logger.info("Player " + player.getName() + " found on " + clientSocket.getInetAddress().getHostAddress() + " socket");


            lobby.getBoardState().setPlayer(player, color);
            game.notifyPlayers("L " + game.JSONLobbies(), clientSockets);
            lobby.sendMessageToAllPlayers("J " + mapper.writeValueAsString(lobby));
            return "J " + mapper.writeValueAsString(lobby);
        }
        catch (IllegalArgumentException e) {
            logger.warn("Invalid color");
            throw new Exception("Invalid color");
        }

    }

}
