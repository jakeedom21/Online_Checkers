package com.webcheckers.utils;

import com.webcheckers.appl.PlayerLobby;
import spark.ModelAndView;
import spark.Request;
import spark.TemplateEngine;

import java.util.Map;

/**
 * Created by qadirhaqq on 3/2/18.
 */
public class ViewUtil {
    private static PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    private static final String FTL_USERNAME = "username";

    public ViewUtil(TemplateEngine templateEngine, PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    public String render(Request request, Map<String, Object> model, String templatePath) {

        return templateEngine.render(new ModelAndView(model, templatePath));
    }
}
