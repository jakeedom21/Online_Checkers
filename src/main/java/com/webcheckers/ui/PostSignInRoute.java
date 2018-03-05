package com.webcheckers.ui;


import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.storage.SessionStorage;
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
public class  PostSignInRoute implements TemplateViewRoute {
    // Constants
    static final String PLAYER = "player";
    static final String MESSAGE_ATTR = "message";
    static final String PLAYER_NAME_ATTR = "playerName";

    private final PlayerLobby playerLobby;
    private final SessionStorage sessionStorage;
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /signin} HTTP request.
     *
     * @param playerLobby
     *   the playerLobby used by the application users
     */
    public PostSignInRoute (final PlayerLobby playerLobby, SessionStorage sessionStorage) {
        // validation
        Objects.requireNonNull(playerLobby, "gameCenter must not be null");
        //
        this.playerLobby = playerLobby;
        this.sessionStorage = sessionStorage;
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
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
//        vm.put("title", "Welcome!");
        LOG.finer("PostSignInRoute is invoked.");
        final String playerName = request.queryParams("name");
        final Session currentSession = request.session();
//        System.out.println(playerName.chars().a);
        if(isAlpha(playerName)){
            if(!(playerLobby.hasUserName(playerName))) {
                playerLobby.addPlayer(playerName);
                sessionStorage.addSession(playerName, currentSession);
                vm.put(PLAYER, playerName);
                currentSession.attribute(PLAYER_NAME_ATTR, playerName);
                LOG.log(Level.INFO,"POST /signin: " + request.session().id() + playerName);
                response.redirect("/");

            } else {
                vm.put(MESSAGE_ATTR, "Name already in use.");
            }
        } else {
            vm.put(MESSAGE_ATTR, "Not a valid name.");
        }


        return null;
//        return new ModelAndView(vm , "signin.ftl");
//        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));

    }

}

