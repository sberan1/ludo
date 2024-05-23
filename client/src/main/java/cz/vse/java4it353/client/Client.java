package cz.vse.java4it353.client;

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
    private Socket clientSocket = new Socket("127.0.0.1", 12345);
    private PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
    private BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    private Thread listenerThread = new Thread(new Listener(clientSocket.getInputStream()));
    private static Client instance = null;


    private Client() throws IOException {

    }

    public static Client getInstance() throws IOException {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void send(String data) {
        try {
            listenerThread.start();
            pw.println(data);
            String odpoved;
            while((odpoved = in.readLine()) != null)
            {
                logger.info(odpoved);
            }

        } catch (IOException e) {
            logger.error("Doslo k vyjimce behem komunikace se serverem.", e);
        }
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
