package cz.vse.java4it353.server.commands;

public interface ICommand {
    String execute(String data) throws Exception;
}
