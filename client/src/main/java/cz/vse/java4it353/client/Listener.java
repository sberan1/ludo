package cz.vse.java4it353.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Listener implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    private List<MessageObserver> observers = new ArrayList<>();
    private InputStream inputStream;

    public Listener(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    public void addObserver(MessageObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MessageObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers(String message) {
        for (MessageObserver observer : observers) {
            observer.onMessageReceived(message);
        }
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                log.debug("Přijatá zpráva: " + line);
                notifyObservers(line);
            }
        } catch (IOException e) {
            log.error("Exception occurred while listening for incoming communication.", e);
        }
    }
}
