package cz.vse.java4it353.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private Map<String, ICommand> commandMap = new HashMap<>();

    public CommandFactory(Socket clientSocket, ServerSocket serverSocket) {
        //commandMap.put("Q", new ShutdownCommand(serverSocket));
        //commandMap.put("E", new DisconnectCommand(clientSocket));
        //commandMap.put("L", new LoginCommand());
        // Add more commands here
    }

    public ICommand getCommand(String commandKey) {
        return commandMap.get(commandKey);
    }
}
