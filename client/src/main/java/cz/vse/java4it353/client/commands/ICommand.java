package cz.vse.java4it353.client.commands;

/**
 * Interface pro příkazy, který definuje, co každý příkaz udělá
 */
public interface ICommand {
    /**
     * Vrací string a zpracovává odeslaný příkaz klientem
     * @param data Data od uživatele
     * @return String a zpracovaná data
     */
    String execute(String data) throws Exception;
}
