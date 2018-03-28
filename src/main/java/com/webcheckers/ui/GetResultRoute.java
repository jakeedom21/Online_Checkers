package com.webcheckers.ui;

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
    private static final String VIEW_WON_STATUS = "won";
    private static final String VIEW_OPPO_FORFEIT = "oppoForfeit";
    private static final String PLAYER_NAME = "playerName";

    //session attributes
    static final String OPPO_FORFEIT_ATTR = "opponentForfeit";  //bool
    static final String GAME_WON_ATTR = "gameWon"; //bool


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
        boolean opponentForfeit = currentSession.attribute(OPPO_FORFEIT_ATTR);
        if (opponentForfeit) {
                vm.put(VIEW_OPPO_FORFEIT, true);
        }

        boolean gameWon = currentSession.attribute(GAME_WON_ATTR);
        if (gameWon) {
            vm.put( VIEW_WON_STATUS, true );
        } else {
            vm.put( VIEW_WON_STATUS, false );
        }
        vm.put(PLAYER_NAME, currentSession.attribute(PLAYER_NAME));
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

}
