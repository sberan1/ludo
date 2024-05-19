package cz.vse.java4it353.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.Socket;

public class Player {
    private int color;
    private String name;
    @JsonIgnore
    private Socket clientSocket;
    Token[] tokens = new Token[4];

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = new Token();
        }
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
