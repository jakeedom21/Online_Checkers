package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by Sameen Luo <xxl2398@rit.edu> on 3/27/2018.
 */
public class GetResignRoute {

    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    public GetResignRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine){
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        //
        LOG.config("GetResignRoute is initialized.");
    }

    public Object handle(Request request, Response response) {

        return null;
    }
}
