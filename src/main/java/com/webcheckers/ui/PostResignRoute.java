package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

/**
 * The UI controller to post resign route.
 * Created by Sameen Luo <xxl2398@rit.edu> on 3/27/2018.
 */
public class PostResignRoute implements Route{

    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

    private final PlayerLobby playerLobby;
    private final Gson gson;


    public PostResignRoute(final PlayerLobby playerLobby, final Gson gson) {

        this.playerLobby = playerLobby;
        this.gson = gson;
        //
        LOG.config("PostResignRoute is initialized.");

    }

    public Object handle(Request request, Response response) {
        final Session currentSession = request.session();
        String playername = currentSession.attribute(PostSignInRoute.PLAYER_NAME_ATTR);
        Player player = playerLobby.getPlayerByUsername(playername);
        Game currentGame = player.getGame();

        if (currentGame == null) {
            System.err.println("Game to resign from is null");
        }

        currentGame.setForfeit(playername);
        response.redirect("/");

        return this.gson.toJson(new Message(Message.MessageType.info, "resign success."));
    }
}

