
import cz.vse.java4it353.server.commands.ChooseColorCommand;
import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.logic.Game;
import cz.vse.java4it353.server.model.Lobby;
import cz.vse.java4it353.server.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ChooseColorCommandTest {
    private ChooseColorCommand command;
    private Socket socket;
    private List<Socket> clientSockets;
    private Game game;
    private Lobby lobby;
    private Player player;

    @BeforeEach
    public void setup() {
        socket = new Socket();
        game = Game.getInstance();
        player = new Player();
        lobby = new Lobby("Test Lobby");
        command = new ChooseColorCommand(socket, clientSockets);
    }

    @Test
    public void testExecute_NullData() {
        assertThrows(Exception.class, () -> command.execute(null));
    }

    @Test
    public void testExecute_InvalidColor() {
        assertThrows(Exception.class, () -> command.execute("INVALID_COLOR"));
    }

    @Test
    public void testExecute_PlayerNotFoundInAnyLobby() throws Exception {
        assertThrows(Exception.class, () -> command.execute("RED"));
    }
    @Test
    public void testExecute_ColorAlreadyTaken() throws Exception {
        lobby.addPlayer(player);
        game.addPlayer(player);
        game.addLobby(lobby);
        player.setClientSocket(socket);
        lobby.getBoardState().setPlayer(new Player(), ColorEnum.GREEN);
        assertThrows(Exception.class, () -> command.execute("GREEN"));
    }

    @Test
    public void testExecute_ValidColor() throws Exception {
        lobby.addPlayer(player);
        game.addPlayer(player);
        game.addLobby(lobby);
        player.setClientSocket(socket);
        //assertEquals(command.execute("GREEN")," ");
        assertNotNull(command.execute("GREEN"));
    }
}
