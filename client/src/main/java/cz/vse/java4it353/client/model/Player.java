package cz.vse.java4it353.client.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Token> tokens = new ArrayList<>();

    public Player() {

    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Token> getTokens() { return tokens; }
    public void setTokens(List<Token> tokens) { this.tokens = tokens; }
}