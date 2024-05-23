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

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private Socket clientSocket;
    private PrintWriter pw;
    private BufferedReader in;
    private Thread listenerThread;
    private static Client instance = null;


    private Client() throws IOException {
        startConnection();
    }

    public static Client getInstance() throws IOException {
        if (instance == null) {
            instance = new Client();

        }
        return instance;
    }
    private void startConnection() throws IOException {
        clientSocket = new Socket("127.0.0.1", 12345);
        pw = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        listenerThread = new Thread(new Listener(clientSocket.getInputStream()));
        listenerThread.start();
    }

    public String send(String data) {
        pw.println(data);
        logger.info("Příkaz odeslán: " + data);
        return receive();
    }
    public String receive() {
        String odpoved;
        try {
            if ((odpoved = in.readLine()) != null) {
                logger.info(odpoved);
                return odpoved;
            }
        } catch (IOException e) {
            logger.error("Během získávání odpovědi se stala chyba.");
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
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
