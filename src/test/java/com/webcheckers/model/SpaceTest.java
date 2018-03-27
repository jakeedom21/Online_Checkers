package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for {@link Space} component.
 */
@Tag("Model-tier")
public class SpaceTest {
    private Space CuT;
    private static final int EVEN_ROW = 2;
    private static final int ODD_COL = 3;
    private static final int MAX_OUTBOUND = 8;
    private static final int MIN_OUTBOUND = -1;
    private static final String PLAYER1_COLOR = "W";

    /**
     * Test that the space is constructed with null piece at a valid location
     */
    @Test
    public void space_withArg() {
        CuT = new Space(EVEN_ROW, ODD_COL);
        assertEquals(null, CuT.getPiece());
        assertTrue(Move.isValid(CuT));
        assertEquals("| |", CuT.toString());
    }

    /**
     * Test valid and invalid spaces
     */
    @Test
    public void piece_on_valid_and_invalid_spaces() {
        // every piece that have an even and odd row column pairing is valid
        for (int row=0; row<MAX_OUTBOUND; row++) {
            for (int col=0; col<MAX_OUTBOUND; col++) {
                CuT = new Space(row, col);
                if(row%2 == 0 && col%2 == 0) {
                    assertFalse(Move.isValid(CuT));
                }
                else if(row%2 == 1 && col%2 == 1) {
                    assertFalse(Move.isValid(CuT));
                }
                else{
                    assertTrue(Move.isValid(CuT));
                }
            }
        }
    }

    /**
     * Test that a square can be occupied with a piece and if it is valid
     * to place a piece on that occupied square
     */
    @Test
    public void space_has_piece() {
        Piece piece = new Piece(EVEN_ROW, ODD_COL, PLAYER1_COLOR);
        CuT = new Space(EVEN_ROW, ODD_COL);
        CuT.setPiece(piece);
        assertFalse(Move.isValid(CuT));
        assertEquals("|W|", CuT.toString());

    }

    /**
     * Test the initialization of a max outbound [8,8] square is invalid
     */
    @Test
    public void max_outbound_space() {
        CuT = new Space(MAX_OUTBOUND, MAX_OUTBOUND);
        assertFalse(Move.isValid(CuT));
    }

    /**
     * Test the initialization of a min outbound [-1,-1] space is invalid
     */
    @Test
    public void min_outbound_space() {
        CuT = new Space(MIN_OUTBOUND, MIN_OUTBOUND);
        assertFalse(Move.isValid(CuT));
    }
}
