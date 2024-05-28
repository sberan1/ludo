package cz.vse.java4it353.server.logic;

import cz.vse.java4it353.server.Main;
import cz.vse.java4it353.server.commands.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private boolean isRunning;
    private final ExecutorService clientHandlingPool;
    private final List<Socket> clientSockets; // Track active client sockets
    private static final Logger log = LoggerFactory.getLogger(Server.class);


    public Server(int port) {
        this.port = port;
        this.clientSockets = Collections.synchronizedList(new ArrayList<>());
        this.clientHandlingPool = Executors.newCachedThreadPool();
        this.isRunning = true;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            log.debug("Server started on port " + port);

            while (isRunning && !serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientSockets.add(clientSocket);
                    log.info("Client connected: " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getInetAddress().getHostName() + "and added to clientSockets");
                    clientHandlingPool.execute(() -> handleClient(clientSocket));
                } catch (SocketException se) {
                    if (serverSocket.isClosed()) {
                        log.info("Server socket closed, stopping server.");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error starting server: " + e.getMessage());
        } finally {
            stop();
        }
    }

    private void handleClient(Socket clientSocket) {
        CommandFactory commandFactory = new CommandFactory(clientSocket, serverSocket, clientSockets); //TODO: transfer more data
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    log.debug("Received message: " + inputLine);
                    String[] input = inputLine.split(" ", 2);

                    if(input.length == 0) {
                        log.error("Empty input");
                        out.println("E Empty input");
                        continue;
                    }

                    ICommand command = commandFactory.getCommand(input[0]);
                    if (command != null) {
                        try {
                            String response = null;
                            if (input.length == 1) {
                                response = command.execute(null);
                                log.info("Response: " + response);
                                out.println(response);
                            } else {
                                response = command.execute(input[1]);
                                log.info("Response: " + response);
                                out.println(response);
                            }
                        } catch (Exception e) {
                            log.error("Error executing command: " + e.getMessage());
                            out.println("E " + e.getMessage());
                        }
                    } else {
                        log.error("Unknown command.");
                        out.println("E Unknown command");
                    }
                }
        } catch (IOException e) {
            log.error("Error handling client: " + e.getMessage());
        }
        finally {
            try {
                clientSocket.close();
                clientSockets.remove(clientSocket);
                log.info("Client disconnected: " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getInetAddress().getHostName() + "and removed from clientSockets");
            } catch (IOException e) {
                log.error("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public void stop() {
        isRunning = false;
        try {
            // Close all client sockets
            for (Socket socket : clientSockets) {
                if (!socket.isClosed()) {
                    socket.close();
                    log.info("Client socket closed: " + socket.getInetAddress().getHostAddress() + ":" + socket.getInetAddress().getHostName());
                }
            }
            // Close the server socket
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                log.info("Server socket closed");
            }
        } catch (IOException e) {
            log.error("Error during server shutdown: " + e.getMessage());
        } finally {
            clientHandlingPool.shutdown(); // Shutdown all client-handling threads
            log.info("Client handling pool shutdown");
            try {
                if (!clientHandlingPool.awaitTermination(30, TimeUnit.SECONDS)) {
                    clientHandlingPool.shutdownNow();
                    log.error("Client handling pool did not shut down gracefully - forcibly shutting down");
                }
            } catch (InterruptedException ie) {
                clientHandlingPool.shutdownNow();
                log.error("Error shutting down client handling pool: " + ie.getMessage() + " - forcibly shutting down");
            }
        }
    }
}
