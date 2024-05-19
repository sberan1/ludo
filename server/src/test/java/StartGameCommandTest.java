import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.server.commands.StartGameCommand;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class StartGameCommandTest {
    private Game game;
    private StartGameCommand command;
    private Socket socket;
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        game = Game.getInstance();
        socket = new Socket();
        command = new StartGameCommand(socket);
        mapper = new ObjectMapper();
    }

    @Test
    public void testExecute_ThrowsWhenNotEnoughPlayers() throws Exception {
        String lobbyName = "TestLobby";
        Lobby lobby = new Lobby(lobbyName);
        Player player = new Player();
        player.setClientSocket(socket);
        lobby.addPlayer(player);
        game.addLobby(lobby);

        assertThrows(Exception.class, () -> command.execute(lobbyName));
    }

    @Test
    public void testExecute_StartsGameWhenEnoughPlayers() throws Exception {
        String lobbyName = "TestLobby";
        Lobby lobby = new Lobby(lobbyName);
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setClientSocket(socket);
        player2.setClientSocket(socket);
        lobby.addPlayer(player1);
        lobby.addPlayer(player2);
        game.addLobby(lobby);

        String result = command.execute(lobbyName);
        assertTrue(game.getLobby(lobbyName).isStarted());
        assertEquals(result, mapper.writeValueAsString(lobby));
    }
}
