package cz.vse.java4it353.server;

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

    public Server(int port) {
        this.port = port;
        this.clientSockets = Collections.synchronizedList(new ArrayList<>());
        this.clientHandlingPool = Executors.newCachedThreadPool();
        this.isRunning = true;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (isRunning && !serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientSockets.add(clientSocket);
                    clientHandlingPool.execute(() -> handleClient(clientSocket));
                } catch (SocketException se) {
                    if (serverSocket.isClosed()) {
                        System.out.println("Server socket closed, stopping server.");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        } finally {
            stop();
        }
    }

    private void handleClient(Socket clientSocket) {
        CommandFactory commandFactory = new CommandFactory(clientSocket, serverSocket);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    ICommand command = commandFactory.getCommand(inputLine);
                    if (command != null) {
                        command.execute();
                    } else {
                        System.out.println("Unknown command.");
                    }
                }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                clientSockets.remove(clientSocket);
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
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
                }
            }
            // Close the server socket
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error during server shutdown: " + e.getMessage());
        } finally {
            clientHandlingPool.shutdown(); // Shutdown all client-handling threads
            try {
                if (!clientHandlingPool.awaitTermination(30, TimeUnit.SECONDS)) {
                    clientHandlingPool.shutdownNow();
                }
            } catch (InterruptedException ie) {
                clientHandlingPool.shutdownNow();
            }
        }
    }
}
