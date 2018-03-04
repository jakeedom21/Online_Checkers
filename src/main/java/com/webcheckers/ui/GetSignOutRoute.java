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
 * The UI Controller to log the user out.
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
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   redirect the user to the home page, logged out
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignOutRoute is invoked.");

        final Session currentSession = request.session();
        String playerName = currentSession.attribute(PostSignInRoute.PLAYER_NAME_ATTR);
        this.playerLobby.removePlayer(playerName);
        this.sessionStorage.removeUserSession(playerName);
        response.redirect("/");

        return null;
    }

}
