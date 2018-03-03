package com.webcheckers.ui;


import com.webcheckers.appl.PlayerLobby;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

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

    private final PlayerLobby playerLobby;
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /signin} HTTP request.
     *
     * @param playerLobby
     *   the playerLobby used by the application users
     */
    public PostSignInRoute (final PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(playerLobby, "gameCenter must not be null");
        //
        this.playerLobby = playerLobby;
    }
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
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
//        System.out.println(playerName.chars().a);
        if(isAlpha(playerName)){
            if(!(playerLobby.hasPlayer(playerName))) {
                playerLobby.addPlayer(playerName, request.session());
                vm.put(PLAYER, playerName);
                LOG.log(Level.INFO,"POST /signin: " + request.session().id() + playerName);
                response.redirect("/");

            } else {
                vm.put(MESSAGE_ATTR, "Name already in use.");
            }
        } else {
            vm.put(MESSAGE_ATTR, "Not a valid name.");
        }

        return new ModelAndView(vm , "signin.ftl");
//        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));

    }

}

