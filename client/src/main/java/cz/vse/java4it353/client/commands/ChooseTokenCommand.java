package cz.vse.java4it353.client.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vse.java4it353.client.model.MovableToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Příkaz pro zvolení tokenů (figurek)
 */
public class ChooseTokenCommand extends Observable implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(CreateLobbyCommand.class);

    /**
     * Konstruktor třídy
     * @param o Observer
     */
    public ChooseTokenCommand(Observer o) {
        addObserver(o);
    }
    @Override
    public String execute(String data) throws Exception {
        log.info("Započal ChooseTokenCommand");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, MovableToken> tokens = objectMapper.readValue(data,
                objectMapper.getTypeFactory().constructMapType(Map.class, Integer.class, MovableToken.class));
        setChanged();
        notifyObservers(tokens);
        log.info("Končí ChooseTokenCommand");
        return null;
    }
}
