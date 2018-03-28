package com.webcheckers.ui;


import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.utils.Constants;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the sign in page.
 *
 */
public class  PostSignInRoute implements Route {
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /signin} HTTP request.
     *
     * @param playerLobby
     *   the playerLobby used by the application users
     */
    public PostSignInRoute (final PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "gameCenter must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }
    public boolean isAlpha(String name) {
        return name.matches("^[a-zA-Z][a-zA-Z0-9]*$");
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
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        LOG.finer("PostSignInRoute is invoked.");
        final String playerName = request.queryParams("name");
        final Session currentSession = request.session();
        if(isAlpha(playerName)){
            if(!(playerLobby.hasUserName(playerName))) {
                playerLobby.addPlayer(playerName);
                vm.put(Constants.PLAYER, playerName);
                currentSession.attribute(Constants.PLAYER_NAME, playerName);
                currentSession.attribute(Constants.SIGNED_IN_PLAYER, true);
                LOG.log(Level.INFO,"POST /signin: " + request.session().id() + playerName);
                response.redirect(Constants.HOME_URL);

            } else {
                vm.put(Constants.MESSAGE, "Name already in use.");
            }
        } else {
            vm.put(Constants.MESSAGE, "Not a valid name.");
        }

        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));

    }

}

