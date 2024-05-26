package cz.vse.java4it353.client.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Token> tokens;

    public Player() {
        this.tokens = new ArrayList<>();
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}