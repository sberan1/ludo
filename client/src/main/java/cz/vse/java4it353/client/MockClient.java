package cz.vse.java4it353.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MockClient {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private Socket clientSocket;
    public PrintWriter pw;
    public BufferedReader in;
    private Listener listener;
    private Thread listenerThread;
    private static MockClient instance = null;

    private MockClient() throws IOException {
        startConnection();
    }

    public static MockClient getInstance() throws IOException {
        if (instance == null) {
            instance = new MockClient();
        }
        return instance;
    }
    private void startConnection() throws IOException {
        clientSocket = new Socket("127.0.0.1", 12345);
        pw = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        listener = new Listener(clientSocket.getInputStream());
        listenerThread = new Thread(listener);
        listenerThread.start();
    }

    public void send() {
        pw.println("");
        logger.info("Příkaz mock clienta odeslán");
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
}