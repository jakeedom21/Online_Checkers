package com.webcheckers.ui;

import com.sun.tools.internal.jxc.ap.Const;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.utils.Constants;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    public static final String TITLE = "Welcome!";
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put(Constants.TITLE, TITLE);
        vm.put(Constants.NUM_USER, playerLobby.getPlayers().size());

        final Session currentSession = request.session();
        if (currentSession.attribute(Constants.SIGNED_IN_PLAYER) != null ) {
            String myUserName = currentSession.attribute(Constants.PLAYER_NAME);
            Player player = playerLobby.getPlayerByUsername(myUserName);
            if(player != null && player.isInGame()){
                response.redirect(Constants.GAME_URL);
            }
        }

        if (currentSession.attribute(Constants.BUSY_OPPONENT_ERROR) == null){
            currentSession.attribute(Constants.BUSY_OPPONENT_ERROR, false);
        }

        String thisPlayerName = currentSession.attribute(Constants.PLAYER_NAME);
        vm.put(Constants.SIGNED_IN_PLAYER, playerLobby.isActiveUser(thisPlayerName));

        vm.put(Constants.BUSY_OPPONENT_ERROR, currentSession.attribute(Constants.BUSY_OPPONENT_ERROR));

        vm.put(Constants.FREE_PLAYERS, playerLobby.getFreePlayerNames(thisPlayerName));
        vm.put(Constants.PLAYER_NAME, thisPlayerName);
        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }

}