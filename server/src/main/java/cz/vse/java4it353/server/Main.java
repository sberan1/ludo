package cz.vse.java4it353.server;

import cz.vse.java4it353.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Server started.");

        try (
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket clientSocket = serverSocket.accept())
        {
            ObjectInputStream ois = new ObjectInputStream(
                    clientSocket.getInputStream());

            boolean keepAlive = true;
            while (keepAlive) {
                Message msg = (Message) ois.readObject();

                System.out.println(msg.getTimestamp() + ": " + msg.getBody());

                if ("QUIT".equals(msg.getBody())) {
                    System.out.println("Ukoncuji spojeni.");
                    keepAlive = false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("Chyba pri praci se socketem.", e);
        }

    }
}