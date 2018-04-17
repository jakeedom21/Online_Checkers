package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MoveTest {
    private static Space mockStart = mock(Space.class);
    private static Space mockEnd = mock(Space.class);
    private static Player player = mock(Player.class);
    private static Move move = new Move(mockStart, mockEnd, player);

    @Test
    void getStart() {
        assertEquals(move.getStart(), mockStart);
    }

    @Test
    void getEnd() {
        assertEquals(move.getEnd(), mockEnd);
    }

    @Test
    void isValid() {
    }
}