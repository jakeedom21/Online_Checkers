package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.Test;
import spark.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReplayGamesRouteTest {

    @Test
    void handle() {
        PlayerLobby playerLobby = mock(PlayerLobby.class);
        TemplateEngine templateEngine = mock(TemplateEngine.class);
        ReplayGamesRoute replayGamesRoute = new ReplayGamesRoute(playerLobby, templateEngine);

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Game game = mock(Game.class);
        Board board = mock(Board.class);

        Request request = mock(Request.class);
        Response response = mock(Response.class);
        Session currentSession = mock(Session.class);
        ArrayList oldGames = mock(ArrayList.class);


        String PLAYER_1_NAME = "PLAYER1";
        String PLAYER_2_NAME = "PLAYER2";

        when(request.session()).thenReturn(currentSession);
        when(request.queryParams("oldGameId")).thenReturn("0");
        when(currentSession.attribute(Constants.PLAYER_NAME)).thenReturn(PLAYER_1_NAME);
        when(playerLobby.getPlayerByUsername(PLAYER_1_NAME)).thenReturn(player1);
        when(player1.getOldGames()).thenReturn(oldGames);
        when(oldGames.get(0)).thenReturn(game);
        when(game.getPlayer1()).thenReturn(player1);
        when(game.getPlayer2()).thenReturn(player2);

        when(player1.getPlayerName()).thenReturn(PLAYER_1_NAME);
        when(player2.getPlayerName()).thenReturn(PLAYER_2_NAME);

        when(game.getBoard(player1)).thenReturn(board);

        ModelAndView modelAndView = mock(ModelAndView.class);

        when(templateEngine.render(modelAndView)).thenReturn("");
        Object object = replayGamesRoute.handle(request, response);
        assertNull(object);
    }
}