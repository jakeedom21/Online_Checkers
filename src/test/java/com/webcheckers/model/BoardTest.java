package com.webcheckers.model;

import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BoardTest {
    Board board = new Board();
    Player player1 = mock(Player.class);
    final int NUM_PIECES_AT_START = 12;


    String expectedBoardSetUp =
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

    @Test
    void setBoardPieces() {
        when(player1.getPieceColor()).thenReturn(Constants.PieceColor.RED);
        // board.setWhitePiecesAtBottom();
        assertTrue(board.toString().equals(expectedBoardSetUp));
        when(player1.getPieceColor()).thenReturn(Constants.PieceColor.RED);
    }

    @Test
    void getP1Pieces() {
        assertEquals(board.getP1Pieces(), NUM_PIECES_AT_START);
    }

    @Test
    void getP2Pieces() {
        assertEquals(board.getP2Pieces(), NUM_PIECES_AT_START);
    }
}