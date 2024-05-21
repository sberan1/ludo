import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Player;
import cz.vse.java4it353.server.commands.LoginCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class LoginCommandTest {
    @Test
    public void testExecute() {
        Socket mockSocket = new Socket(); // Create a mock socket
        LoginCommand loginCommand = new LoginCommand(mockSocket);
        String testName = "TestPlayer";
        loginCommand.execute(testName);

        Game game = Game.getInstance();
        Player player = game.listPlayers().stream()
                .filter(p -> p.getName().equals(testName))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(player, "Player should not be null");
        assertEquals(testName, player.getName(), "Player name should match the test name");
        assertEquals(mockSocket, player.getClientSocket(), "Player's client socket should match the mock socket");
    }
}
