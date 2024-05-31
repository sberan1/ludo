package cz.vse.java4it353.client.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.client.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;
import java.util.Observer;

public class WinnerCommand extends Observable implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(SyncLobbiesCommand.class);
    public WinnerCommand(Observer o) {
        addObserver(o);
    }
    @Override
    public String execute(String data) throws Exception {
        log.info("Započal WinnerCommand");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        Player player = objectMapper.treeToValue(jsonNode, Player.class);

        setChanged();
        notifyObservers(player);
        log.info("Končí StartGameCommand");
        return null;
    }
}
