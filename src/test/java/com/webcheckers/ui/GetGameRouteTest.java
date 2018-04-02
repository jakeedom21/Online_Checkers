package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetGameRouteTest {

    PlayerLobby playerLobby = mock(PlayerLobby.class);
    TemplateEngine templateEngine = mock(TemplateEngine.class);
    Request request = mock(Request.class);
    Response response = mock(Response.class);
    Session currentSession = mock(Session.class);
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);
    Game game = mock(Game.class);

    @Test
    void renderGamePage() {

        GetGameRoute getGameRoute = new GetGameRoute(playerLobby, templateEngine);
        when(player1.getPlayerName()).thenReturn("player1");
        when(player2.getPlayerName()).thenReturn("player2");
        when(playerLobby.getPlayerByUsername("player1")).thenReturn(player1);
        when(request.queryParams("opponentName")).thenReturn("player2");
        when(playerLobby.getPlayerByUsername("player2")).thenReturn(player2);
        when(currentSession.attribute("playerName")).thenReturn("player1");
        when(request.session()).thenReturn(currentSession);
        when(player1.getPieceColor()).thenReturn(Constants.PieceColor.RED);
        when(game.getPlayer1()).thenReturn(player1);
        when(game.getPlayer2()).thenReturn(player2);
        when(player1.getGame()).thenReturn(game);

        getGameRoute.renderGamePage(request, response);


        Player[] activePlayers = {player1, player2};
        assertEquals(game.getPlayer1(), player1);
        assertEquals(game.getPlayer2(), player2);
    }
}