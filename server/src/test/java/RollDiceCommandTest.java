import cz.vse.java4it353.server.commands.RollDiceCommand;
import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.exception.ForbiddenMoveException;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Board;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class RollDiceCommandTest {
    private Game game;
    private Lobby lobby;
    private Board board;
    private Player player;
    private Socket socket;
    private RollDiceCommand command;

    @BeforeEach
    public void setUp() {
        game = Game.getInstance();
        lobby = new Lobby("Test Lobby");
        board = lobby.getBoardState();
        player = new Player();
        player.setName("Karel");
        socket = new Socket();
        command = new RollDiceCommand(socket);
    }

    @Test
    public void testExecute_lobbyNotStarted() {
        lobby.setStarted(false);

        assertThrows(ForbiddenMoveException.class, () -> {
            command.execute("");
        });
    }

    @Test
    public void testExecute_playerNotFound() {
        lobby.setPlayers(new Player[]{});

        assertThrows(ForbiddenMoveException.class, () -> {
            command.execute("");
        });
    }

    @Test
    public void testExecute_playerNotOnTurn() {
        Player anotherPlayer = new Player();
        anotherPlayer.setName("Another Player");
        board.setPlayerOnTurn(anotherPlayer);

        assertThrows(ForbiddenMoveException.class, () -> {
            command.execute("");
        });
    }

    @Test
    public void testExecute_correctRun() throws Exception {
        player.setClientSocket(socket);
        Player greenPlayer = new Player();
        lobby.addPlayer(player);
        game.addLobby(lobby);
        lobby.addPlayer(greenPlayer);
        board.setPlayer(player, ColorEnum.RED);
        board.setPlayer(greenPlayer, ColorEnum.GREEN);
        lobby.setStarted(true);
        board.setPlayerOnTurn(player);

        assertDoesNotThrow(() -> {
            command.execute("");
        });
    }
}
