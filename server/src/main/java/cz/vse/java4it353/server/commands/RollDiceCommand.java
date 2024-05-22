package cz.vse.java4it353.server.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.exception.ForbiddenMoveException;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Board;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import cz.vse.java4it353.server.model.Token;

import java.net.Socket;
import java.util.Map;

public class RollDiceCommand implements ICommand{
    private final Socket clientSocket;
    private final ObjectMapper mapper = new ObjectMapper();


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
                if (player != null && player.getClientSocket() == clientSocket && player.equals(playerOnTurn)) {
                    board.rollDice();
                    Map<Integer, Token> tokens = player.getMovableTokens(board.getDiceValue());
                    if (tokens.isEmpty()) {
                        board.nextPlayerOnTurn();
                        lobby.sendMessageToAllPlayers(mapper.writeValueAsString(board));
                        throw new ForbiddenMoveException("No tokens can be moved, next player on turn");
                    }
                    return "T " + mapper.writeValueAsString(tokens);
                }
                else {
                    throw new ForbiddenMoveException("Player not found or not on turn");
                }
            }
        }
            throw new ForbiddenMoveException("Lobby is not started, can't make a move");
    }
}