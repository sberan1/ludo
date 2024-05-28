package cz.vse.java4it353.client.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.client.model.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;
import java.util.Observer;

public class CreateLobbyCommand extends Observable implements ICommand {

    private static final Logger log = LoggerFactory.getLogger(CreateLobbyCommand.class);

    public CreateLobbyCommand(Observer o) {
        addObserver(o);
    }
    @Override
    public String execute(String data) throws Exception {
        log.info("Teď jsem v CreateLobbyCommand");
        log.debug(data);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        Lobby lobby = objectMapper.treeToValue(jsonNode, Lobby.class);

        setChanged();
        notifyObservers(lobby);
        return null;
    }
}
