package com.webcheckers.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by Jake on 3/20/2018.
 * This is the unit test for Game
 */
@Tag("Model-tier")
public class GameTest {
    private Game GAME;
    private Player PLAYER1;
    private Player PLAYER2;
    private static final String PLAYER1NAME = "JIM";
    private static final String PLAYER2NAME = "ALSOJIM";
    private static final int ID = 613432;

    private String expectedPlayer1BoardSetUp =
            "\n------------------------\n" +
                    "| ||W|| ||W|| ||W|| ||W|\n" +
                    "------------------------\n" +
                    "|W|| ||W|| ||W|| ||W|| |\n" +
                    "------------------------\n" +
                    "| ||W|| ||W|| ||W|| ||W|\n" +
                    "------------------------\n" +
                    "| || || || || || || || |\n" +
                    "------------------------\n" +
                    "| || || || || || || || |\n" +
                    "------------------------\n" +
                    "|R|| ||R|| ||R|| ||R|| |\n" +
                    "------------------------\n" +
                    "| ||R|| ||R|| ||R|| ||R|\n" +
                    "------------------------\n" +
                    "|R|| ||R|| ||R|| ||R|| |\n" +
                    "------------------------\n";
    private String expectedPlayer2BoardSetUp =
            "\n------------------------\n" +
                    "| ||R|| ||R|| ||R|| ||R|\n" +
                    "------------------------\n" +
                    "|R|| ||R|| ||R|| ||R|| |\n" +
                    "------------------------\n" +
                    "| ||R|| ||R|| ||R|| ||R|\n" +
                    "------------------------\n" +
                    "| || || || || || || || |\n" +
                    "------------------------\n" +
                    "| || || || || || || || |\n" +
                    "------------------------\n" +
                    "|W|| ||W|| ||W|| ||W|| |\n" +
                    "------------------------\n" +
                    "| ||W|| ||W|| ||W|| ||W|\n" +
                    "------------------------\n" +
                    "|W|| ||W|| ||W|| ||W|| |\n" +
                    "------------------------\n";
    private String expectedPlayer1BoardSetUpAfterOneMove =
            "\n------------------------\n" +
                    "| ||W|| ||W|| ||W|| ||W|\n" +
                    "------------------------\n" +
                    "|W|| ||W|| ||W|| ||W|| |\n" +
                    "------------------------\n" +
                    "| ||W|| ||W|| ||W|| ||W|\n" +
                    "------------------------\n" +
                    "| || || || || || || || |\n" +
                    "------------------------\n" +
                    "| ||R|| || || || || || |\n" +
                    "------------------------\n" +
                    "| || ||R|| ||R|| ||R|| |\n" +
                    "------------------------\n" +
                    "| ||R|| ||R|| ||R|| ||R|\n" +
                    "------------------------\n" +
                    "|R|| ||R|| ||R|| ||R|| |\n" +
                    "------------------------\n";
    private String expectedPlayer2BoardSetUpAfterOneMove =
            "\n------------------------\n" +
                    "| ||R|| ||R|| ||R|| ||R|\n" +
                    "------------------------\n" +
                    "|R|| ||R|| ||R|| ||R|| |\n" +
                    "------------------------\n" +
                    "| ||R|| ||R|| ||R|| || |\n" +
                    "------------------------\n" +
                    "| || || || || || ||R|| |\n" +
                    "------------------------\n" +
                    "| || || || || || || || |\n" +
                    "------------------------\n" +
                    "|W|| ||W|| ||W|| ||W|| |\n" +
                    "------------------------\n" +
                    "| ||W|| ||W|| ||W|| ||W|\n" +
                    "------------------------\n" +
                    "|W|| ||W|| ||W|| ||W|| |\n" +
                    "------------------------\n";

    @BeforeEach
    void setUp(){
        PLAYER1 = spy(new Player(PLAYER1NAME));
        PLAYER2 = spy(new Player(PLAYER2NAME));
        GAME = spy(new Game(ID, PLAYER1, PLAYER2));
    }

    @Test
    void getPlayerTurn() {
        assertTrue(GAME.getPlayerTurn().equals(PLAYER1NAME));
    }

    @Test
    void finishMove() {
        assertTrue(GAME.getPlayerTurn().equals(PLAYER1NAME));
        GAME.finishMove();
        assertTrue(GAME.getPlayerTurn().equals(PLAYER2NAME));
    }

    @Test
    void getId() {
        assertEquals(GAME.getId(), ID);
    }

    @Test
    void getBoard() {
        assertEquals(this.GAME.getBoard(PLAYER1).toString(), expectedPlayer1BoardSetUp);
        assertEquals(this.GAME.getBoard(PLAYER2).toString(), expectedPlayer2BoardSetUp);
    }

    @Test
    void getWinner() {
        Board board = mock(Board.class);
        when(GAME.getBoard(PLAYER1)).thenReturn(board);
        when(board.getP1Pieces()).thenReturn(0);
        when(board.getP2Pieces()).thenReturn(0);
        doReturn(PLAYER2NAME).when(GAME).getWinner();
        assertEquals(GAME.getWinner(), PLAYER2.getPlayerName());
    }

    @Test
    void isWinner() {
        Board board = mock(Board.class);
        when(board.getP1Pieces()).thenReturn(0);
        when(GAME.getBoard(PLAYER1)).thenReturn(board);
        when(GAME.getBoard(PLAYER2)).thenReturn(board);
    }

    @Test
    void setForfeit() {
        GAME.setForfeit(PLAYER1.getPlayerName());
        assertEquals(GAME.getWinner(), PLAYER2.getPlayerName());
        GAME.setForfeit(PLAYER2.getPlayerName());
        assertEquals(GAME.getWinner(), PLAYER1.getPlayerName());
    }

    @Test
    void didPlayerResign() {
        assertFalse(GAME.didPlayerResign());
        GAME.setForfeit(PLAYER1.getPlayerName());
        assertTrue(GAME.didPlayerResign());
    }

    @Test
    void queueMove() {
        Move move = mock(Move.class);
        GAME.queueMove(move);
        assertEquals(GAME.getNextMove(), move);
    }

    @Test
    void getNextMove() {
        Move move = mock(Move.class);
        GAME.queueMove(move);
        assertEquals(GAME.getNextMove(), move);
    }

    @Test
    void movePiece() {
        Move move = mock(Move.class);
        Space start = mock(Space.class);
        Space end = mock(Space.class);

        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);
        when(start.getRow()).thenReturn(5);
        when(start.getCol()).thenReturn(0);
        when(end.getRow()).thenReturn(4);
        when(end.getCol()).thenReturn(1);

        GAME.movePiece(start, end, PLAYER1);

        assertEquals(GAME.getBoard(PLAYER1).toString(), expectedPlayer1BoardSetUpAfterOneMove);
        assertEquals(GAME.getBoard(PLAYER2).toString(), expectedPlayer2BoardSetUpAfterOneMove);
    }
}
