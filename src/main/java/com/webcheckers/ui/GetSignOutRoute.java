package com.webcheckers.ui;


import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.utils.Constants;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to sign the user out.
 *
 */
public class GetSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignOutRoute.class.getName());

    private final PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param playerLobby
     *    the player lobby shared object
     */
    public GetSignOutRoute(final PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        // set the instance variables
        this.playerLobby = playerLobby;
        // log that the class has now been initialized
        LOG.config("GetSignOutRoute is initialized.");
    }

    /**
     * Sign the user out.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   null, redirect the user to the home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignOutRoute is invoked.");

        final Session currentSession = request.session();

        // get the player name from the session
        String playerName = currentSession.attribute(Constants.PLAYER_NAME);

        // remove the player from the PlayerLobby and SessionStorage
        this.playerLobby.removePlayer(playerName);

        // set the SIGNED_IN session attribute to null
        currentSession.attribute(Constants.SIGNED_IN_PLAYER, null);

        // redirect the user back to the home page after signing out
        response.redirect(Constants.HOME_URL);
        return null;
    }

}
