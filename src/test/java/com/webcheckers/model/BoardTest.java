package com.webcheckers.model;

import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BoardTest {
    private Board board = new Board();
    private final int NUM_PIECES_AT_START = 12;

    private Space[][] rawBoard = new Space[Constants.MAX_DIM][Constants.MAX_DIM];

    Space mockStartSpace = mock(Space.class);
    Space mockEndSpace = mock(Space.class);


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


    @Test
    void getP1Pieces() {
        assertEquals(board.getP1Pieces(), NUM_PIECES_AT_START);
    }

    @Test
    void getP2Pieces() {
        assertEquals(board.getP2Pieces(), NUM_PIECES_AT_START);
    }


    @Test
    void movePiece() {

        when(mockStartSpace.getRow()).thenReturn(5);
        when(mockStartSpace.getCol()).thenReturn(0);

        when(mockEndSpace.getRow()).thenReturn(4);
        when(mockEndSpace.getCol()).thenReturn(1);

        this.board.movePiece(mockStartSpace, mockEndSpace);

        assertNull(board.getSpace(5,0).getPiece());
        assertNotNull(board.getSpace(4, 1).getPiece());
    }

    @Test
    void getSpace() {
        Board board = mock(Board.class);
        when(board.getSpace(5, 0)).thenReturn(mockStartSpace);
        assertEquals(board.getSpace(5, 0), mockStartSpace);
    }

    @Test
    void flip() {
        this.board.flip();
        assertEquals(this.board.toString(), expectedPlayer2BoardSetUp);
        this.board.flip();
        assertEquals(this.board.toString(), expectedPlayer1BoardSetUp);
    }
}