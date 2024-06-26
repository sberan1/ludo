package cz.vse.java4it353.server.logic;

import cz.vse.java4it353.server.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory for creating commands
 *
 * @author sberan
 */
public class CommandFactory {
    private Map<String, ICommand> commandMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);

    /**
     * Constructor
     * @param clientSocket socket of current player
     * @param serverSocket socket of the server
     * @param clientSockets list of all client sockets
     */
    public CommandFactory(Socket clientSocket, ServerSocket serverSocket, List<Socket> clientSockets) {
        //commandMap.put("E", new DisconnectCommand(clientSocket));
        commandMap.put("L", new LoginCommand(clientSocket, clientSockets));
        commandMap.put("C", new CreateLobbyCommand(clientSocket, clientSockets));
        commandMap.put("J", new JoinLobbyCommand(clientSocket, clientSockets));
        commandMap.put("S", new StartGameCommand(clientSocket, clientSockets));
        commandMap.put("CC", new ChooseColorCommand(clientSocket, clientSockets));
        commandMap.put("R", new RollDiceCommand(clientSocket));
        commandMap.put("M", new MoveTokenCommand(clientSocket));
    }

    /**
     * Returns command by key
     * @param commandKey key of the command
     * @return command
     */
    public ICommand getCommand(String commandKey) {
        ICommand command = commandMap.get(commandKey);
        if (command == null) {
            logger.warn("Command " + commandKey + " not found");
        }
        return command;
    }
}
