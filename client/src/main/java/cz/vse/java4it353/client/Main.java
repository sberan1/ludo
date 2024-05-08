package cz.vse.java4it353.client;

import cz.vse.java4it353.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Client started.");

        try (
            Scanner scanner = new Scanner(System.in);
            Socket clientSocket = new Socket("127.0.0.1", 8080))
        {
            ObjectOutputStream oos = new ObjectOutputStream(
                    clientSocket.getOutputStream());

            boolean keepAlive = true;
            while (keepAlive) {
                String line = scanner.nextLine();

                if ("QUIT".equals(line)) {
                    keepAlive = false;
                }

                Message msg = new Message(line);

                oos.writeObject(msg);
            }
        } catch (IOException e) {
            log.error("Doslo k vyjimce behem komunikace se serverem.", e);
        }
    }
}