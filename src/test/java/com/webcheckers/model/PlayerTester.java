package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.Player.PieceColor.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author Sameen Luo <xxl2398@rit.edu> on 3/20/2018
 */
@Tag("Model-tier")
public class PlayerTester {

    private static final Player.PieceColor piececolor = RED;
    private static final String playername = "StormRed";
    private static final String opponentname = "MidBoss";
    private static final Game currentgame = mock(Game.class);
    private static final Player opponent = mock(Player.class);

    /**
     * The component-under-test (CuT).
     */
    private Player CuT;

    // friendly objects


    /**
     * Test that the main constructor works without failure.
     */
    @Test
    public void creator(){
        new Player(playername);
    }

    /**
     * Test that the assignGame method works without failure.
     */
    @Test
    public void testAssignGame(){

        when(opponent.getPlayerName()).thenReturn(opponentname);

        Player CuT = new Player((playername));
        CuT.assignGame(piececolor,currentgame,opponent);

        assertTrue(CuT.isInGame());
        assertEquals(CuT.getPieceColor(),piececolor);
        assertEquals(CuT.getGame(), currentgame);
        assertEquals(CuT.getOpponentName(), opponentname);
    }

    /**
     * Test that the finishGame method works without failure.
     */
    @Test
    public void TestFinishGame(){
        when(opponent.getPlayerName()).thenReturn(opponentname);

        Player CuT = new Player((playername));
        CuT.assignGame(piececolor,currentgame,opponent);

        CuT.finishGame();
        assertFalse(CuT.isInGame());
        assertEquals(CuT.getPieceColor(),null);
        assertEquals(CuT.getGame(), null);
        assertEquals(CuT.getOpponentName(), null);
    }




}
