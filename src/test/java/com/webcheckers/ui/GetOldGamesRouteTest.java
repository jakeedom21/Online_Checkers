package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.utils.Constants;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetOldGamesRouteTest {

    @Test
    void handle() {
        PlayerLobby playerLobby = mock(PlayerLobby.class);
        TemplateEngine templateEngine = mock(TemplateEngine.class);
        GetOldGamesRoute getOldGamesRoute = new GetOldGamesRoute(playerLobby, templateEngine);

        Player player = mock(Player.class);
        Game game = mock(Game.class);

        Request request = mock(Request.class);
        Response response = mock(Response.class);
        Session currentSession = mock(Session.class);

        ModelAndView modelAndView = mock(ModelAndView.class);

        String PLAYER_1 = "PLAYER1";

        when(request.session()).thenReturn(currentSession);
        when(currentSession.attribute(Constants.PLAYER_NAME)).thenReturn(PLAYER_1);
        when(playerLobby.getPlayerByUsername(PLAYER_1)).thenReturn(player);
        when(player.getGame()).thenReturn(game);


        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Object object = getOldGamesRoute.handle(request, response);
        System.out.println("Object: " + object);
        assertNull(object);

    }
}