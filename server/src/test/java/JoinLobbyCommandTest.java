import cz.vse.java4it353.server.commands.JoinLobbyCommand;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.ILoggerFactory;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinLobbyCommandTest {
    @Test
    public void testExecute() {
        List<Socket> mockSockets = new ArrayList<>(); // Create a mock list of sockets
        Socket mockSocket = new Socket(); // Create a mock socket

        // Create a player and add it to the game
        Player testPlayer = new Player();
        testPlayer.setName("TestPlayer");
        testPlayer.setClientSocket(mockSocket);
        Game.getInstance().addPlayer(testPlayer);

        // Create a lobby and add it to the game
        Lobby testLobby = new Lobby("TestLobby");
        Game.getInstance().addLobby(testLobby);

        JoinLobbyCommand joinLobbyCommand = new JoinLobbyCommand(mockSocket, mockSockets);
        String output = null;
        try {
            output = joinLobbyCommand.execute(testLobby.getName());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        Game game = Game.getInstance();
        Lobby lobby = game.getLobby(testLobby.getName());

        Player player = game.getPlayerBySocket(mockSocket);

        Assertions.assertEquals(output, game.JSONLobbies(), "Output should match the JSON lobbies");
        Assertions.assertNotNull(lobby, "Lobby should not be null");
        Assertions.assertEquals(testLobby.getName(), lobby.getName(), "Lobby name should match the test name");
        Assertions.assertNotNull(player, "Player should not be null");
        Assertions.assertTrue(Arrays.asList(lobby.getPlayers()).contains(player), "Lobby should contain the player");    }
}
