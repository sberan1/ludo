import cz.vse.java4it353.server.commands.MoveTokenCommand;
import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.exception.ForbiddenMoveException;
import cz.vse.java4it353.server.exception.IncorrectlyDefinedArgumentException;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Board;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import cz.vse.java4it353.server.model.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTokenCommandTest {
    private Game game;
    private Lobby lobby;
    private Player player;
    private Board board;
    private Socket socket;
    private MoveTokenCommand command;

    @BeforeEach
    public void setup() throws IncorrectlyDefinedArgumentException {
        game = Game.getInstance();
        lobby = new Lobby("Test Lobby");
        board = new Board();
        socket = new Socket();
        player = new Player("Test Player", socket);
        command = new MoveTokenCommand(socket);


    }

    @Test
    public void moveTokenSuccessfully() throws Exception {
        game.addPlayer(player);
        game.addLobby(lobby);
        lobby.addPlayer(player);
        lobby.setBoardState(board);
        var otherPlayer = new Player("Other Player", new Socket());
        lobby.addPlayer(otherPlayer);
        lobby.getBoardState().setPlayer(player, ColorEnum.RED);
        lobby.getBoardState().setPlayer(otherPlayer, ColorEnum.BLUE);
        lobby.setStarted(true);
        board.setPlayerOnTurn(player);
        board.rollDice();
        board.setDiceValue(6);
        board.setHasPlayerRolled(true);

        assertDoesNotThrow(() -> command.execute("1"));


    }

    @Test
    public void moveTokenPlayerNotInLobby() {
        game.addPlayer(player);

        assertThrows(Exception.class, () -> command.execute("1"));
    }



    @Test
    public void moveTokenPlayerHasNotRolledDice() throws Exception {
        game.addPlayer(player);
        game.addLobby(lobby);
        lobby.addPlayer(player);
        lobby.setBoardState(board);
        var otherPlayer = new Player("Other Player", new Socket());
        lobby.addPlayer(otherPlayer);
        lobby.getBoardState().setPlayer(player, ColorEnum.RED);
        lobby.getBoardState().setPlayer(otherPlayer, ColorEnum.BLUE);
        lobby.setStarted(true);
        board.setPlayerOnTurn(player);
        board.rollDice();
        board.setDiceValue(6);
        board.setHasPlayerRolled(false);

        assertThrows(ForbiddenMoveException.class, () -> command.execute("1"));
    }

    @Test
    public void moveTokenPlayerNotOnTurn() throws Exception{
        game.addPlayer(player);
        game.addLobby(lobby);
        lobby.addPlayer(player);
        lobby.setBoardState(board);
        var otherPlayer = new Player("Other Player", new Socket());
        lobby.addPlayer(otherPlayer);
        lobby.getBoardState().setPlayer(player, ColorEnum.RED);
        lobby.getBoardState().setPlayer(otherPlayer, ColorEnum.BLUE);
        lobby.setStarted(true);
        board.setPlayerOnTurn(otherPlayer);
        board.setHasPlayerRolled(true);

        assertThrows(ForbiddenMoveException.class, () -> command.execute("1"));
    }

    @Test
    public void moveTokenInvalidTokenIndex() throws Exception {
        game.addPlayer(player);
        game.addLobby(lobby);
        lobby.addPlayer(player);
        lobby.setBoardState(board);
        var otherPlayer = new Player("Other Player", new Socket());
        lobby.addPlayer(otherPlayer);
        lobby.getBoardState().setPlayer(player, ColorEnum.RED);
        lobby.getBoardState().setPlayer(otherPlayer, ColorEnum.BLUE);
        lobby.setStarted(true);
        board.setPlayerOnTurn(player);
        board.setHasPlayerRolled(true);

        assertThrows(ForbiddenMoveException.class, () -> command.execute("5"));
    }
}
