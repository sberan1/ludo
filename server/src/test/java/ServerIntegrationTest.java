
import cz.vse.java4it353.server.logic.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerIntegrationTest {
    private Server server;
    private Thread serverThread;

    @BeforeEach
    public void setup() {
        server = new Server(8080);
        serverThread = new Thread(() -> server.start());
        serverThread.start();
    }

    @Test
    public void testServerResponse() throws Exception {
        try (Socket clientSocket = new Socket("localhost", 8080);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String testCommand = "L TestPlayer";
            out.println(testCommand);

            String response = in.readLine();
            assertEquals("{}", response, "Server response should match expected");
        }
    }

    @AfterEach
    public void tearDown() {
        server.stop();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
