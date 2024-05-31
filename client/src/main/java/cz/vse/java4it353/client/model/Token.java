package cz.vse.java4it353.client.model;

/**
 * Třída pro vytvoření tokenu (figurky)
 */
public class Token {
    private int position;
    public int move(int steps) {
        position += steps;
        return position;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}