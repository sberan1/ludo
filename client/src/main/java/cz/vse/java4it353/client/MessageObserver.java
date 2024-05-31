package cz.vse.java4it353.client;

/**
 * Interface pro zajištění zpráv ze serveru
 */
public interface MessageObserver {
    /**
     * Metoda pro zpracování odpovědi serveru
     * @param message Data
     */
    void onMessageReceived(String message);
}