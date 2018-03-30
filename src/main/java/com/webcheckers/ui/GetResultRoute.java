package com.webcheckers.ui;

//import com.sun.tools.internal.jxc.ap.Const;
import com.webcheckers.utils.Constants;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by Sameen Luo <xxl2398@rit.edu> on 3/24/2018.
 */
public class GetResultRoute implements Route{

    //result.ftl vars
    private static final String VIEW_NAME = "result.ftl";

    private final TemplateEngine templateEngine;
    private static final Logger LOG = Logger.getLogger(GetResultRoute.class.getName());

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetResultRoute (final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        LOG.config("GetResultRoute is initialized.");
    }

    /**
     * Render the WebCheckers Sign in page.
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
        // retrieve the HTTP session
        final Session currentSession = request.session();

        LOG.finer("GetResultRoute is invoked.");
        //

        // start building the View-Model
        Map<String, Object> vm = new HashMap<>();
        boolean opponentForfeit = currentSession.attribute(Constants.OPPONENT_FORFEIT);
        if (opponentForfeit) {
            vm.put(Constants.OPPONENT_FORFEIT, true);
        }

        boolean gameWon = currentSession.attribute(Constants.GAME_WON);
        if (gameWon) {
            vm.put(Constants.GAME_WON, true);
        } else {
            vm.put(Constants.GAME_WON, false);
        }
        vm.put(Constants.PLAYER_NAME, currentSession.attribute(Constants.PLAYER_NAME));
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

}
