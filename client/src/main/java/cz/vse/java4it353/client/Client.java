package cz.vse.java4it353.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static Client instance = null;


    private Client() {

    }

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void send(String data) {
        try (
                Socket clientSocket = new Socket("127.0.0.1", 8080);
                PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            Thread listenerThread = new Thread(new Listener(clientSocket.getInputStream()));
            listenerThread.start();

            pw.println(data);
        } catch (IOException e) {
            logger.error("Doslo k vyjimce behem komunikace se serverem.", e);
        }
    }
}
