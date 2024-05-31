package cz.vse.java4it353.server.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.exception.ForbiddenMoveException;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Board;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import cz.vse.java4it353.server.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.Map;

/**
 * Command for rolling dice
 *
 * @version 1.0.0
 * @author sberan
 */
public class RollDiceCommand implements ICommand{
    private static final Logger logger = LoggerFactory.getLogger(RollDiceCommand.class);
    private final Socket clientSocket;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor
     * @param clientSocket socket of current player
     */
    public RollDiceCommand(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public String execute(String data) throws Exception {
        Game game = Game.getInstance();
        Lobby lobby = game.getLobbyWithPlayer(clientSocket);
        if (lobby != null && lobby.isStarted()) {
            Board board = lobby.getBoardState();
            Player playerOnTurn = board.getPlayerOnTurn();
            for (Player player : lobby.getPlayers()) {
                if (player != null && player.getClientSocket().equals(clientSocket) && player.equals(playerOnTurn)) {
                    int newValue = board.rollDice();
                    logger.info("Player " + player.getName() + " rolled dice with value " + newValue);
                    Map<Integer, Token> tokens = player.getMovableTokens(board.getDiceValue());
                    if (tokens.isEmpty()) {
                        board.nextPlayerOnTurn();
                        lobby.sendMessageToAllPlayers("B " + mapper.writeValueAsString(board));
                        return "M No tokens can be moved, next player on turn";
                    }
                    lobby.sendMessageToAllPlayers("B " + mapper.writeValueAsString(board));
                    return "T " + mapper.writeValueAsString(tokens);
                }
            }
            throw new ForbiddenMoveException("Player is not in lobby or is not his turn");
        }
        throw new ForbiddenMoveException("Lobby is not started, can't make a move");
    }
}
