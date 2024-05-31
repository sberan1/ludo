package cz.vse.java4it353.client;

/**
 * Interface pro zajištění znovupřipojení klienta k serveru
 */
public interface IConnectionLostHandler {
    /**
     * Metoda pro připojení zpět na server
     */
    void onConnectionLost();
}
