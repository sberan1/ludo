package cz.vse.java4it353.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {
    private String name;
    private String uuid;
    private List<Token> tokens = new ArrayList<>();
    public Player() {
        uuid = UUID.randomUUID().toString();
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Token> getTokens() { return tokens; }
    public void setTokens(List<Token> tokens) { this.tokens = tokens; }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}