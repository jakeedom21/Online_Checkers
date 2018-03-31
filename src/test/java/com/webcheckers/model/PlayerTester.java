package com.webcheckers.model;

import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.utils.Constants.PieceColor.RED;
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

    private static final Constants.PieceColor piececolor = RED;
    private static final String playername = "StormRed";
    private static final String opponentname = "MidBoss";
    private static Game currentgame;
    private static Player opponent;

    /**
     * The component-under-test (CuT).
     */
    private Player CuT;

    /**
     * Set up before each test
     */
    @BeforeEach
    public void setUp(){
        currentgame = mock(Game.class);
        opponent = mock(Player.class);
        //when(opponent.getPlayerName()).thenReturn(opponentname);
    }

    /**
     * Test that the main constructor works without failure.
     */
    @Test
    public void creator(){
        Player newbie = new Player(playername);
        assertEquals(playername, newbie.getPlayerName());
    }

    /**
     * Test that the assignGame method works without failure.
     */
    @Test
    public void testAssignGame(){

        Player CuT = new Player((playername));
        CuT.assignGame(piececolor, currentgame, opponent);

        assertTrue(CuT.isInGame());
        assertEquals(piececolor, CuT.getPieceColor());
        assertEquals(currentgame, CuT.getGame());
        assertEquals(null,CuT.getOpponentName());
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
