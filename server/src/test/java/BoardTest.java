import cz.vse.java4it353.server.enums.ColorEnum;
import cz.vse.java4it353.server.exception.IncorrectlyDefinedArgumentException;
import cz.vse.java4it353.server.model.Board;
import cz.vse.java4it353.server.model.Player;
import cz.vse.java4it353.server.model.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BoardTest {
    private Board board;
    private Player player1;
    private Player player2;
    private Token token1;
    private Token token2;
    private Token token3;
    private Token token4;

    @BeforeEach
    public void setup() {
        board = new Board();
        player1 = Mockito.mock(Player.class);
        player2 = Mockito.mock(Player.class);
        token1 = Mockito.mock(Token.class);
        token2 = Mockito.mock(Token.class);
        token3 = Mockito.mock(Token.class);
        token4 = Mockito.mock(Token.class);
    }

    @Test
    public void playerWinsWhenAllTokensInExactLastFourPlaces() throws IncorrectlyDefinedArgumentException {
        when(player1.getTokens()).thenReturn(new Token[]{token1, token2, token3, token4});
        when(token1.getPosition()).thenReturn(41);
        when(token2.getPosition()).thenReturn(42);
        when(token3.getPosition()).thenReturn(43);
        when(token4.getPosition()).thenReturn(44);
        board.setPlayer(player1, ColorEnum.RED);
        assertEquals(player1, board.checkGameFinished());
    }
    @Test
    public void noPlayerWinsWhenTokensNotInExactLastFourPlaces() throws IncorrectlyDefinedArgumentException {
        when(player1.getTokens()).thenReturn(new Token[]{token1, token2, token3, token4});
        when(token1.getPosition()).thenReturn(40);
        when(token2.getPosition()).thenReturn(42);
        when(token3.getPosition()).thenReturn(43);
        when(token4.getPosition()).thenReturn(44);
        board.setPlayer(player1, ColorEnum.RED);
        assertNull(board.checkGameFinished());
    }

    @Test
    public void noPlayerWinsWhenTwoTokensOnSamePlace() throws IncorrectlyDefinedArgumentException {
        when(player1.getTokens()).thenReturn(new Token[]{token1, token2, token3, token4});
        when(token1.getPosition()).thenReturn(41);
        when(token2.getPosition()).thenReturn(42);
        when(token3.getPosition()).thenReturn(43);
        when(token4.getPosition()).thenReturn(43);
        board.setPlayer(player1, ColorEnum.RED);
        assertNull(board.checkGameFinished());
    }
}
