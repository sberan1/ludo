package cz.vse.java4it353.client.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.client.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Příkaz pro zpracování nového stavu desky
 */
public class StartGameCommand extends Observable implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(SyncLobbiesCommand.class);

    /**
     * Konstruktor třídy
     * @param o Observer
     */
    public StartGameCommand(Observer o) {
        addObserver(o);
    }
    @Override
    public String execute(String data) throws Exception {
        log.info("Započal StartGameCommand");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        Board board = objectMapper.treeToValue(jsonNode, Board.class);

        setChanged();
        notifyObservers(board);
        log.info("Končí StartGameCommand");
        return null;
    }
}
