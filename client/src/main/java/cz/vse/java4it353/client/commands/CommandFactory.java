package cz.vse.java4it353.client.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

public class CommandFactory {
    private Map<String, ICommand> commandMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(CommandFactory.class);
    public CommandFactory(Observer o) {
        /*commandMap.put("L", new LoginCommand());
        commandMap.put("C", new CreateLobbyCommand());
        commandMap.put("J", new JoinLobbyCommand());
        commandMap.put("S", new StartGameCommand());
        commandMap.put("CC", new ChooseColorCommand());
        commandMap.put("R", new RollDiceCommand());
        commandMap.put("M", new MoveTokenCommand());*/
        commandMap.put("L", new SyncLobbiesCommand(o));
        commandMap.put("J", new CreateLobbyCommand(o));
        commandMap.put("B", new StartGameCommand(o));
        commandMap.put("T", new ChooseTokenCommand(o));
        //.put("E", null);
    }

    public ICommand getCommand(String commandKey) {
        ICommand command = commandMap.get(commandKey);
        if (command == null) {
            logger.warn("Command " + commandKey + " not found");
        }
        return command;
    }
}