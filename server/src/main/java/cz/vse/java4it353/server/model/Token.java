package cz.vse.java4it353.server.model;

public class Token {

    private int position;

    public int move(int steps) {
        if (position == 0){
            if (steps == 6){
                position = 1;
            }
        }
        else {
            position += steps;
        }
        return position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
