package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class PlayerLobbyTest {

    PlayerLobby PLAYERLOBBY;
    Player PLAYER1  = mock(Player.class);
    String PLAYER1_NAME = "Player1";
    String PLAYER2_NAME = "Player2";

    @BeforeEach
    void setUp(){
        PLAYERLOBBY = spy(new PlayerLobby());
    }

    @Test
    void addPlayer() {
        PLAYERLOBBY.addPlayer(PLAYER1_NAME);
        assertNotNull(PLAYERLOBBY.getPlayerByUsername(PLAYER1_NAME));
    }

    @Test
    void removePlayer() {
        PLAYERLOBBY.addPlayer(PLAYER1_NAME);
        PLAYERLOBBY.removePlayer(PLAYER1_NAME);
        assertFalse(PLAYERLOBBY.getPlayers().contains(PLAYER1_NAME));
    }

    @Test
    void isActiveUser() {
        PLAYERLOBBY.addPlayer(PLAYER1_NAME);
        assertTrue(PLAYERLOBBY.isActiveUser(PLAYER1_NAME));
    }

    @Test
    void hasUserName() {
        PLAYERLOBBY.addPlayer(PLAYER1_NAME);
        assertTrue(PLAYERLOBBY.hasUserName(PLAYER1_NAME));
    }

    @Test
    void getPlayers() {
        PLAYERLOBBY.addPlayer(PLAYER1_NAME);

        assertEquals(PLAYERLOBBY.getPlayers().size(), 1);
    }

    @Test
    void getPlayerByUsername() {
        PLAYERLOBBY.addPlayer(PLAYER1_NAME);
        assertEquals(PLAYERLOBBY.getPlayerByUsername(PLAYER1_NAME).getPlayerName(), PLAYER1_NAME);

    }

    @Test
    void getFreePlayerNames() {
        PLAYERLOBBY.addPlayer(PLAYER1_NAME);
        PLAYERLOBBY.addPlayer(PLAYER2_NAME);
        assertTrue(PLAYERLOBBY.getFreePlayerNames(PLAYER2_NAME).contains(PLAYER1_NAME));
    }
}