package cz.vse.java4it353.server.exception;

/**
 * Exception thrown when an argument is incorrectly defined
 *
 * @author sberan
 */
public class IncorrectlyDefinedArgumentException extends Exception{
    /**
     * Constructor
     * @param message message to be displayed
     */
    public IncorrectlyDefinedArgumentException(String message) {
        super(message);
    }
}
