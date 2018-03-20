package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.model.Game;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
/**
 * Created by Jake on 3/20/2018.
 * This is the unit test for Game
 */
@Tag("Model-tier")
public class GameTest {
    private static final Player BADPLAYER = null;
    private static final Player GOODPLAYER = new Player("JIM");
    private static final Player GOODPLAYER2 = new Player("ALSOJIM");
    private static final Board BADBOARD = null;
    private static final Board GOODBOARD = new Board();
    private static final int GOODID = 5124;


    @Test
    public void test_bad_player(){
        final Game CuT = new Game(GOODID, BADPLAYER, GOODPLAYER);
        assertThrow
    }
}
