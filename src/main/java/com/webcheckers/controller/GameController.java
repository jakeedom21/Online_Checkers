package com.webcheckers.controller;


import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.RouteManager;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qadirhaqq on 2/27/18.
 */
public class GameController {

    private static final String GAME_FTL = "game.ftl";
    PlayerLobby playerLobby;
    public GameController(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    public String createNewGame(Request request, Response response) {
        // initialize new game
        Player p1 = playerLobby.getPlayerBySession(request.session().id());
        if (p1 == null){
            // redirect
        }
        final String opponentName = request.queryParams("opponentName");
        Player p2 = playerLobby.getPlayer(opponentName);

        // createNewGame
        Game g = new Game(p1, p2);

        p1.setGame(g);
        p2.setGame(g);


        response.redirect(RouteManager.GAMES_ROUTE);

        return null;
    }

    public String renderGamePage(Request request, Response response) {
        // talk to FTL

        String sess = request.session().id();
        Player p1 = playerLobby.getPlayerBySession(sess);
        String opponentName = request.queryParams("opponentName");
        Player p2 = playerLobby.getPlayer(opponentName);

        Game p1_game = p1.getGame();
        Game p2_game = p2.getGame();
        if (p1_game != p2_game) {
            // throw some error
        } else {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("title", String.format("Game #%d (%s vs. %s)", p1_game.getId(), p1.getPlayerName(), p2.getPlayerName()));
            attributes.put("playerColor", playerColor);
            attributes.put("opponentName", opponent.getUsername());
            attributes.put("opponentColor", opponentColor);
            attributes.put("isMyTurn", isMyTurn);

        }

        return null;
    }
}
