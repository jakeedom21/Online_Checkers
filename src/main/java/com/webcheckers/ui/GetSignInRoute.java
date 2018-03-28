package com.webcheckers.ui;


import com.webcheckers.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.*;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class  GetSignInRoute implements Route {
    static final String TITLE = "Welcome!";
    static final String VIEW_NAME = "signin.ftl";

    // Key in the session attribute map for the player who started the session
    static final String PLAYERSERVICES_KEY = "playerServices";
    static final String TIMEOUT_SESSION_KEY = "timeoutWatchdog";

    // The length of the session timeout in seconds
    static final int SESSION_TIMEOUT_PERIOD = 120;

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignInRoute (final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("GetSignInRoute is initialized.");
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
        final Session httpSession = request.session();

        LOG.finer("GetSignInRoute is invoked.");
        //

        // start building the View-Model
        Map<String, Object> vm = new HashMap<>();
        vm.put(Constants.TITLE, TITLE);

        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }

}
