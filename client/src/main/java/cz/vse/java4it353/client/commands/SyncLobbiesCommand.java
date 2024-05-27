package cz.vse.java4it353.client.commands;

import cz.vse.java4it353.client.model.Lobby;
import javafx.beans.InvalidationListener;

import java.util.Observable;
import java.util.Observer;

public class SyncLobbiesCommand extends Observable implements ICommand {
    public SyncLobbiesCommand(Observer o) {
        addObserver(o);
    }
    @Override
    public String execute(String data) throws Exception {
        // do stuff
        notifyObservers(new Lobby());
        return null;
    }
}
