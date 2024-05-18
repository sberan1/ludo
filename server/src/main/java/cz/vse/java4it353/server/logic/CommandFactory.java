package cz.vse.java4it353.server.logic;

import cz.vse.java4it353.server.commands.CreateLobbyCommand;
import cz.vse.java4it353.server.commands.ICommand;
import cz.vse.java4it353.server.commands.JoinLobbyCommand;
import cz.vse.java4it353.server.commands.LoginCommand;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandFactory {
    private Map<String, ICommand> commandMap = new HashMap<>();

    public CommandFactory(Socket clientSocket, ServerSocket serverSocket, List<Socket> clientSockets) {
        //commandMap.put("Q", new ShutdownCommand(serverSocket));
        //commandMap.put("E", new DisconnectCommand(clientSocket));
        commandMap.put("L", new LoginCommand(clientSocket));
        commandMap.put("C", new CreateLobbyCommand(clientSocket, clientSockets));
        commandMap.put("J", new JoinLobbyCommand(clientSocket));
        // Add more commands here
    }

    public ICommand getCommand(String commandKey) {
        return commandMap.get(commandKey);
    }
}
