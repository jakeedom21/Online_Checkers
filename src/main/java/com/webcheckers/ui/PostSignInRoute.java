package com.webcheckers.ui;


import com.webcheckers.appl.PlayerLobby;
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
    // Constants
    static final String PLAYER = "player";
    static final String MESSAGE_ATTR = "message";
    static final String PLAYER_NAME_ATTR = "playerName";

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
//        vm.put("title", "Welcome!");
        LOG.finer("PostSignInRoute is invoked.");
        final String playerName = request.queryParams("name");
        final Session currentSession = request.session();
//        System.out.println(playerName.chars().a);
        if(isAlpha(playerName)){
            if(!(playerLobby.hasUserName(playerName))) {
                playerLobby.addPlayer(playerName);
                vm.put(PLAYER, playerName);
                currentSession.attribute(PLAYER_NAME_ATTR, playerName);
                currentSession.attribute(GetHomeRoute.SIGNED_IN_ATTR, true);
                LOG.log(Level.INFO,"POST /signin: " + request.session().id() + playerName);
                response.redirect("/");

            } else {
                vm.put(MESSAGE_ATTR, "Name already in use.");
            }
        } else {
            vm.put(MESSAGE_ATTR, "Not a valid name.");
        }

//        return new ModelAndView(vm , "signin.ftl");
        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));

    }

}

