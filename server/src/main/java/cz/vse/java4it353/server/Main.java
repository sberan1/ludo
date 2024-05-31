package cz.vse.java4it353.server;

import cz.vse.java4it353.server.logic.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class of the server
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Main method
     * @param args arguments
     */
    public static void main(String[] args) {
        Server server = new Server(12345);
        server.start();
    }
}
