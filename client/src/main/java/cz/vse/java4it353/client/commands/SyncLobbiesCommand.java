package cz.vse.java4it353.client.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.client.model.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Příkaz pro synchronizaci lobbies
 */
public class SyncLobbiesCommand extends Observable implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(SyncLobbiesCommand.class);

    /**
     * Konstruktor třídy
     * @param o Observer
     */
    public SyncLobbiesCommand(Observer o) {
        addObserver(o);
    }
    @Override
    public String execute(String data) throws Exception {
        log.info("Započal SyncLobbiesCommand");
        List<Lobby> lobbies = new ArrayList<>();

        if(data.equalsIgnoreCase("{}")) {
            log.info("Data prázdná, vyskakuji ze SyncLobbiesCommand");
            notifyObservers(lobbies);
            log.info("Končí SyncLobbiesCommand");
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            JsonNode value = field.getValue();
            Lobby lobby = objectMapper.treeToValue(value, Lobby.class);
            lobbies.add(lobby);
        }

        setChanged();
        notifyObservers(lobbies);
        log.info("Končí SyncLobbiesCommand");
        return null;
    }
}
