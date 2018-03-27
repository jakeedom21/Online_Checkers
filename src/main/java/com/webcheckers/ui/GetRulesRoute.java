package com.webcheckers.ui;


import java.util.HashMap;
import java.util.Objects;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Rules page.
 */
public class GetRulesRoute implements Route {
    public static final String VIEW_NAME = "rules.ftl";
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetRulesRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
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
     *   the rendered HTML for the Rules page
     */
    @Override
    public Object handle(Request request, Response response) {
        return templateEngine.render(new ModelAndView(new HashMap<>(), VIEW_NAME));
    }

}
