package com.webcheckers.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    void setUp(){
        PLAYER1 = new Player(PLAYER1NAME);
        PLAYER2 = new Player(PLAYER2NAME);
        GAME = new Game(ID, PLAYER1, PLAYER2);
    }

    @Test
    void getPlayers() {
        assertTrue(GAME.getPlayer1().getPlayerName().equals(PLAYER1NAME));
        assertTrue(GAME.getPlayer2().getPlayerName().equals(PLAYER2NAME));
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
}
