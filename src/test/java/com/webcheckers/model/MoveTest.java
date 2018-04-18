package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        Space space = mock(Space.class);
        when(space.getRow()).thenReturn(0);
        when(space.getCol()).thenReturn(5);
        assertTrue(move.isValid(space));
    }
}