package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 * Created by Brandon Dossantos <bxd7887@rit.edu> on 3/29/2018.
 */
@Tag("UI-tier")
public class PostResignRouteTest {

    private static final String TEST_PLAYER_ONE = "player1";
    private static final String TEST_PLAYER_TWO = "player2";
    private static final int TEST_GAME_ID = 99;
    // component under test
    private PostResignRoute CuT;
    // mock objects
    private Request request;
    private Response response;
    private Session session;

    // friendly
    private PlayerLobby playerLobby;
    private Game game;

    /**
     * Setup mock objects to test
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);

        when(session.attribute(Constants.PLAYER_NAME)).thenReturn(TEST_PLAYER_ONE);

        playerLobby = new PlayerLobby();

        playerLobby.addPlayer(TEST_PLAYER_ONE);
        playerLobby.addPlayer(TEST_PLAYER_TWO);
        Player test_player_1 = playerLobby.getPlayerByUsername(TEST_PLAYER_ONE);
        Player test_player_2 = playerLobby.getPlayerByUsername(TEST_PLAYER_TWO);

        game = new Game(TEST_GAME_ID, test_player_1, test_player_2);
        CuT = new PostResignRoute(playerLobby);
    }


    @Test
    public void post_resign_route() {

        assertFalse(game.didPlayerResign());
        assertFalse(game.isGameWon());

        CuT.handle(request, response);
        assertTrue(game.didPlayerResign());
        assertTrue(game.isGameWon());
        assertEquals(TEST_PLAYER_TWO, game.getWinner());
    }
    @AfterEach
    public void teardown(){
        playerLobby.removePlayer(TEST_PLAYER_ONE);
        playerLobby.removePlayer(TEST_PLAYER_TWO);
    }
}
