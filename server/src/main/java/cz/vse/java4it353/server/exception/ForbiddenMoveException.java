package cz.vse.java4it353.server.exception;

/**
 * Exception thrown when a forbidden move is attempted
 *
 * @author sberan
 */
public class ForbiddenMoveException extends Exception{
    /**
     * Constructor
     * @param message message to be displayed
     */
    public ForbiddenMoveException(String message) {
        super(message);
    }
}
