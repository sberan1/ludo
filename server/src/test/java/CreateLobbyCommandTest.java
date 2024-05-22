import cz.vse.java4it353.server.commands.CreateLobbyCommand;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateLobbyCommandTest {
    @Test
    public void testExecute() {
        List<Socket> mockSockets = new ArrayList<>(); // Create a mock list of sockets
        Socket mockSocket = new Socket(); // Create a mock socket

        // Create a player and add it to the game
        Player testPlayer = new Player();
        testPlayer.setName("TestPlayer");
        testPlayer.setClientSocket(mockSocket);
        Game.getInstance().addPlayer(testPlayer);

        CreateLobbyCommand createLobbyCommand = new CreateLobbyCommand(mockSocket, mockSockets);
        String testLobbyName = "TestLobby";
        String output = null;
        try {
            output = createLobbyCommand.execute(testLobbyName);
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }

        Game game = Game.getInstance();
        Lobby lobby = game.listLobbies().stream()
                .filter(l -> l.getName().equals(testLobbyName))
                .findFirst()
                .orElse(null);

        Player player = game.listPlayers().stream()
                .filter(p -> p.getClientSocket().equals(mockSocket))
                .findFirst()
                .orElse(null);

        Assertions.assertEquals(output, game.JSONLobbies(), "Output should match the JSON lobbies");
        Assertions.assertNotNull(lobby, "Lobby should not be null");
        Assertions.assertEquals(testLobbyName, lobby.getName(), "Lobby name should match the test name");
        Assertions.assertNotNull(player, "Player should not be null");
        Assertions.assertTrue(Arrays.asList(lobby.getPlayers()).contains(player), "Lobby should contain the player");
    }
}
