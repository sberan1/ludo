package cz.vse.java4it353.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Třída pro vytvoření klienta
 */
public class Client implements IConnectionLostHandler {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private Socket clientSocket;
    public PrintWriter pw;
    public BufferedReader in;
    private Listener listener;
    private Thread listenerThread;
    private static Client instance = null;
    private final int retryDelay = 5000; // 5000 ms = 5 s
    private boolean isConnectionLost;

    private Client() throws IOException {
        startConnection();
    }

    /**
     * @return Instanci klienta
     * @throws IOException
     */
    public static Client getInstance() throws IOException {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }
    private void startConnection() throws IOException {
        isConnectionLost = false;
        clientSocket = new Socket("127.0.0.1", 12345);
        pw = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        listener = new Listener(clientSocket.getInputStream(), this);
        listenerThread = new Thread(listener);
        listenerThread.start();
    }
    private void connect() throws IOException {
        startConnection();
        send("L " + Main.getPlayerName());
    }

    public void send(String data) {
        new Thread(() -> {
            pw.println(data);
            logger.info("Příkaz odeslán: " + data);
        }).start();
    }
    public void addObserver(MessageObserver observer) {
        listener.addObserver(observer);
    }

    public void removeObserver(MessageObserver observer) {
        listener.removeObserver(observer);
    }

    public void closeConnection() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            logger.error("Exception occurred while closing connection.", e);
        }
    }
    public void stop() throws IOException {
        listener.stop();
        try {
            listenerThread.join();  // Počkejte, až se listener ukončí
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        clientSocket.close();
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }
    }

    @Override
    public void onConnectionLost() {
        logger.warn("Connection lost. Attempting to reconnect...");
        isConnectionLost = true;
        while (isConnectionLost) {
            try {
                connect();
                logger.info("Reconnected to the server.");
                break;
            } catch (IOException e) {
                logger.error("Reconnection attempt failed. Retrying in " + retryDelay / 1000 + " seconds...", e);
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
