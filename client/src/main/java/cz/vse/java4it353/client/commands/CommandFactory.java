package cz.vse.java4it353.client.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

/**
 * Třída pro shromáždění všech příkazů, které jsou zde namapovány
 */
public class CommandFactory {
    private Map<String, ICommand> commandMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(CommandFactory.class);

    /**
     * Konstruktor třídy CommandFactory, kde jsou namapovány všechny příkazy
     * @param o Observer, který bude reagovat na činnosti v příkazech
     */
    public CommandFactory(Observer o) {
        commandMap.put("L", new SyncLobbiesCommand(o));
        commandMap.put("J", new CreateLobbyCommand(o));
        commandMap.put("B", new StartGameCommand(o));
        commandMap.put("T", new ChooseTokenCommand(o));
        commandMap.put("W", new WinnerCommand(o));
        commandMap.put("E", new ErrorCommand(o));
    }

    /**
     * Metoda pro získání příkazu, na základě kterého se provede činnost
     * @param commandKey Znak, podle kterého se zjistí příkaz
     * @return Příkaz
     */
    public ICommand getCommand(String commandKey) {
        ICommand command = commandMap.get(commandKey);
        if (command == null) {
            logger.warn("Command " + commandKey + " not found");
        }
        return command;
    }
}