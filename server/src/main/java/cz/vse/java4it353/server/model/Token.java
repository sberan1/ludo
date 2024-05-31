package cz.vse.java4it353.server.model;

/**
 * Class representing a token
 *
 *
 * @author sberan
 */
public class Token {

    private int position;

    /**
     * Logic for moving the token
     *
     * @author sberan
     */
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


    /**
     * Get position of the token
     * @return position of the token
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set position of the token - for example reset to 0 when other token lands on its head
     *
     * @param position position of the token
     */
    public void setPosition(int position) {
        this.position = position;
    }
}
