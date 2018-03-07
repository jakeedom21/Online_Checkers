package com.webcheckers.ui;


import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.storage.SessionStorage;
import java.util.Objects;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * The UI Controller to sign the user out.
 *
 */
public class GetSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignOutRoute.class.getName());

    private final PlayerLobby playerLobby;
    private final SessionStorage sessionStorage;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param playerLobby
     *    the player lobby shared object
     * @param sessionStorage
     *    the shared session storage object
     */
    public GetSignOutRoute(final PlayerLobby playerLobby, final SessionStorage sessionStorage) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(sessionStorage, "sessionStorage must not be null");
        // set the instance variables
        this.playerLobby = playerLobby;
        this.sessionStorage = sessionStorage;
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
        String playerName = currentSession.attribute(GetHomeRoute.PLAYER_NAME_ATTR);

        // remove the player from the PlayerLobby and SessionStorage
        this.playerLobby.removePlayer(playerName);
        this.sessionStorage.removeUserSession(playerName);

        // set the SIGNED_IN session attribute to null
        currentSession.attribute(GetHomeRoute.SIGNED_IN_ATTR, null);

        // redirect the user back to the home page after signing out
        response.redirect("/");
        return null;
    }

}
