import cz.vse.java4it353.server.model.Player;
import cz.vse.java4it353.server.model.Token;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    public class PlayerTest {

        @Test
        public void getMovableTokensReturnsEmptyMapWhenNoTokensCanMove() {
            // Given a player with tokens that cannot move
            Player player = new Player("Test Player", mock(Socket.class));
            for (Token token : player.getTokens()) {
                token.setPosition(0); // Tokens at position 0 cannot move unless diceValue is 6
            }

            // When getMovableTokens is called with a diceValue other than 6
            Map<Integer, Token> result = player.getMovableTokens(3);

            // Then the result is an empty map
            assertTrue(result.isEmpty());
        }

        @Test
        public void getMovableTokensReturnsMapWithMovableTokensWhenDiceValueIsSix() {
            // Given a player with tokens that can move
            Player player = new Player("Test Player", mock(Socket.class));
            for (Token token : player.getTokens()) {
                token.setPosition(0); // Tokens at position 0 can move when diceValue is 6
            }

            // When getMovableTokens is called with a diceValue of 6
            Map<Integer, Token> result = player.getMovableTokens(6);

            // Then the result is a map containing all tokens
            assertEquals(player.getTokens().length, result.size());
            for (Token token : result.values()) {
                assertTrue(Arrays.asList(player.getTokens()).contains(token));
            }
        }

        @Test
        public void getMovableTokensReturnsMapWithMovableTokensWhenTokensAreOnBoard() {
            // Given a player with tokens that are on the board
            Player player = new Player("Test Player", mock(Socket.class));
            for (Token token : player.getTokens()) {
                token.setPosition(1); // Tokens on the board can move when diceValue is not 6
            }

            // When getMovableTokens is called with a diceValue other than 6
            Map<Integer, Token> result = player.getMovableTokens(3);

            // Then the result is a map containing all tokens
            assertEquals(player.getTokens().length, result.size());
            for (Token token : result.values()) {
                assertTrue(Arrays.asList(player.getTokens()).contains(token));
            }
        }

        @Test
        public void getMovableTokensReturnsNullWhenItWouldOverflow(){
            // Given a player with a token that would overflow
            Player player = new Player("Test Player", mock(Socket.class));
            for (Token token : player.getTokens()) {
                token.setPosition(57); // Tokens on the board can move when diceValue is not 6
            }

            // When getMovableTokens is called with a diceValue of 6
            Map<Integer, Token> result = player.getMovableTokens(6);

            // Then the result is null
            assertTrue(result.isEmpty());
        }

        @Test
        public void getMovableTokensWhenOneTokenCanMove(){
            // Given a player with a token that can move
            Player player = new Player("Test Player", mock(Socket.class));
            for (Token token : player.getTokens()) {
                token.setPosition(0); // Tokens at position 0 can move when diceValue is 6
            }
            player.getTokens()[0].setPosition(1);

            // When getMovableTokens is called with a diceValue of 6
            Map<Integer, Token> result = player.getMovableTokens(2);

            // Then the result is a map containing one token
            assertEquals(1, result.size());
            assertTrue(Arrays.asList(player.getTokens()).contains(result.get(0)));
        }
    }

