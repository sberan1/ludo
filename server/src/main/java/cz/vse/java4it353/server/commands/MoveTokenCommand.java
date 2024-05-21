package cz.vse.java4it353.server.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.exception.ForbiddenMoveException;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Board;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;

import java.lang.management.PlatformLoggingMXBean;
import java.net.Socket;

public class MoveTokenCommand implements ICommand {
    private final Socket clientSocket;
    ObjectMapper mapper = new ObjectMapper();

    public MoveTokenCommand(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public String execute(String data) throws Exception {
        Game game = Game.getInstance();
        Lobby lobby = game.getLobbyWithPlayer(clientSocket);
        Player player = game.getPlayerBySocket(clientSocket);
        Board board = lobby.getBoardState();
        validateCommand(board, player, data);
        int tokenIndex = Integer.parseInt(data);
        player.moveToken(tokenIndex, board.getDiceValue(), lobby);
        board.nextPlayerOnTurn();

        return "B " + mapper.writeValueAsString(lobby.getBoardState());
    }

    private void validateCommand(Board board, Player player, String data) throws ForbiddenMoveException {
        if (board == null) {
            throw new ForbiddenMoveException("Player not in any lobby");
        }
        if (!board.hasPlayerRolled()) {
            throw new ForbiddenMoveException("Player has not rolled the dice yet");
        }
        if (player == null) {
            throw new ForbiddenMoveException("Player not found");
        }
        if (player != board.getPlayerOnTurn()) {
            throw new ForbiddenMoveException("Player not on turn");
        }
        if (data == null || data.isEmpty()) {
            throw new ForbiddenMoveException("No token specified");
        }
        int tokenIndex = Integer.parseInt(data);
        if (tokenIndex < 0 || tokenIndex >= player.getTokens().length) {
            throw new ForbiddenMoveException("Invalid token index");
        }
    }
}
