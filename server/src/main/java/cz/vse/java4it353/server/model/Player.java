package cz.vse.java4it353.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.Socket;

public class Player {
    private String name;
    @JsonIgnore
    private Socket clientSocket;
    Token[] tokens = new Token[4];

    public Player(String name, Socket clientSocket) {
        this.name = name;
        this.clientSocket = clientSocket;
    }

    public Player() {
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = new Token();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Token[] getTokens() {
        return tokens;
    }
    public void moveToken(int tokenIndex, int steps) {
        tokens[tokenIndex].move(steps);
    }
}
