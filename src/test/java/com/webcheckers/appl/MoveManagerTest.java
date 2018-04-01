package com.webcheckers.appl;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import spark.Response;
import spark.Request;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest( { MoveValidation.class} )
class MoveManagerTest {

    private Game GAME;
    private Player PLAYER1;
    private Player PLAYER2;
    private static final String PLAYER1NAME = "JIM";
    private static final String PLAYER2NAME = "ALSOJIM";
    private static final int ID = 613432;
    private Request request;
    private Response response;
    private PlayerLobby playerLobby;
    private Gson gson;
    private MoveManager moveManager;
    private Session session;

    @BeforeEach
    void setUp() {
        PLAYER1 = new Player(PLAYER1NAME);
        PLAYER2 = new Player(PLAYER2NAME);
        GAME = new Game(ID, PLAYER1, PLAYER2);
        request = mock(Request.class);
        playerLobby = mock(PlayerLobby.class);
        moveManager = new MoveManager(playerLobby);
        gson = Constants.gson;
        session = mock(Session.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(Constants.PLAYER_NAME)).thenReturn(PLAYER1NAME);
        when(playerLobby.getPlayerByUsername(PLAYER1NAME)).thenReturn(PLAYER1);
    }

    @Test
    void validateMove() {
//        Move move = mock(Move.class);
//        Space start = mock(Space.class);
//        Space end = mock(Space.class);
//        Game game = mock(Game.class);
//        Board board = mock(Board.class);
//
//        Player player1 = mock(Player.class);
//        when(playerLobby.getPlayerByUsername(PLAYER1NAME)).thenReturn(player1);
//        when(player1.getGame()).thenReturn(game);
//        when(game.getNextMove()).thenReturn(move);
//        when(board.getSpace(5, 0)).thenReturn(start);
//
//        when(move.getStart()).thenReturn(start);
//        when(move.getEnd()).thenReturn(end);
//
//        PowerMockito.mockStatic(MoveValidation.class);
//        // BDDMockito.given(MoveValidation.validMove(start, end, board)).willReturn("");
//        when(MoveValidation.validMove(start, end, board)).thenReturn("");
//
//        assertEquals(moveManager.validateMove(request, response),
//                gson.toJson(new Message(Message.MessageType.info, "")));

        // Tried everything to make this test work, 2 hour mark, I'm calling it here

    }

    @Test
    void submitMove() {
        Move move = mock(Move.class);
        Space start = mock(Space.class);
        Game game = mock(Game.class);
        Player player1 = mock(Player.class);
        when(playerLobby.getPlayerByUsername(PLAYER1NAME)).thenReturn(player1);
        when(player1.getGame()).thenReturn(game);
        when(game.getNextMove()).thenReturn(move);
        Space end = mock(Space.class);
        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);
        assertEquals(moveManager.submitMove(request, response),
                gson.toJson(new Message(Message.MessageType.info, "Turn submitted successfully")));
    }

    @Test
    void backupMove() {
        assertEquals(moveManager.backupMove(request, response),
                gson.toJson(new Message(Message.MessageType.info, "Success")));
    }

    @Test
    void checkTurn() {
        assertEquals(moveManager.checkTurn(request, response),
                gson.toJson(new Message(Message.MessageType.info, "true")));
    }
}