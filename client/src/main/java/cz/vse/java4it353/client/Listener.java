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
    private final List<MessageObserver> observers = new ArrayList<>();
    private final InputStream inputStream;
    private volatile boolean running = true;

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
        synchronized (observers) {
            for (MessageObserver observer : observers) {
                observer.onMessageReceived(message);
            }
        }
    }
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            log.info("Zde začíná naslouchání na listeneru");
            while (running && (line = br.readLine()) != null) {
                log.debug("Přijatá zpráva: "+ line);
                notifyObservers(line);
            }
        } catch (IOException e) {
            if (running)
                log.error("Exception occurred while listening for incoming communication.", e);
             else
                log.info("Listener was stopped.");
        }
    }
}
