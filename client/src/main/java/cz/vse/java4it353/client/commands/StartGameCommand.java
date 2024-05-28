package cz.vse.java4it353.client.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.client.model.Board;
import cz.vse.java4it353.client.model.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class StartGameCommand extends Observable implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(SyncLobbiesCommand.class);
    public StartGameCommand(Observer o) {
        addObserver(o);
    }
    @Override
    public String execute(String data) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        Board board = objectMapper.treeToValue(jsonNode, Board.class);

        log.info("Vracím board a tím spouštím hru");
        setChanged();
        notifyObservers(board);
        return null;
    }
}
