package cz.vse.java4it353.server.model;

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
