package cz.vse.java4it353.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.Socket;

public class Player {
    private int color;
    private String name;
    @JsonIgnore
    private Socket clientSocket;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
}
