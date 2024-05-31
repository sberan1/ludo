package cz.vse.java4it353.server.commands;

/**
 * Interface pro command pattern
 *
 * @author sberan
 */
public interface ICommand {
    /**
     * Executes the command
     * @param data data to be processed
     * @return result of the command
     * @throws Exception if the command fails
     */
    String execute(String data) throws Exception;
}
