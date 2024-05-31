package cz.vse.java4it353.client.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;
import java.util.Observer;

/**
 * Příkaz pro vyhození erroru
 */
public class ErrorCommand extends Observable implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(ErrorCommand.class);

    /**
     * Konstruktor třídy
     * @param o Observer
     */
    public ErrorCommand(Observer o) {
        addObserver(o);
    }
    @Override
    public String execute(String data) {
        log.error("Nastal error: " + data);
        return null;
    }
}
