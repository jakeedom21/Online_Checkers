package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class  GetHomeRoute implements Route {

  static final String TITLE_ATTR = "title";
  static final String TITLE = "Welcome!";
  static final String NUM_USER_ATTR = "numUsers";
  static final String SIGNED_IN_ATTR = "signedInPlayer";
  static final String FREE_PLAYERS_ATTR = "freePlayers";
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

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
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);
    vm.put(NUM_USER_ATTR, playerLobby.getPlayers().size());

    final Session currentSession = request.session();
    String thisPlayerName = currentSession.attribute(PLAYER_NAME_ATTR);
    vm.put(SIGNED_IN_ATTR, playerLobby.isActiveUser(thisPlayerName));
    vm.put(FREE_PLAYERS_ATTR, playerLobby.getFreePlayerNames(thisPlayerName));
    System.out.println(vm);
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }

}