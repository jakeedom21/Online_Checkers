package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.utils.Constants;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.utils.Constants.PLAYER_NAME;
import static com.webcheckers.utils.Constants.SIGNED_IN_PLAYER;

public class ReplayGamesRoute implements Route {

    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    /**
     * Constructor for the replayGames route
     * @param playerLobby - playerLobby object
     * @param templateEngine - template Engine object
     */
    public ReplayGamesRoute(PlayerLobby playerLobby, TemplateEngine templateEngine ) {
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Handle the server request for the replayed game
     * @param request - spark request
     * @param response - spark response
     * @return String of the rendered ".ftl"
     */
    @Override
    public Object handle(Request request, Response response) {
        Session currentSession = request.session();
        String playerName = currentSession.attribute(Constants.PLAYER_NAME);
        Player player = playerLobby.getPlayerByUsername(playerName);

        int oldGameId = Integer.parseInt(request.queryParams("oldGameId"));
        Game game = player.getOldGames().get(oldGameId);

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Replay Game for " + playerName);
        vm.put(SIGNED_IN_PLAYER, playerLobby.isActiveUser(player.getPlayerName()));
        vm.put(PLAYER_NAME, currentSession.attribute(PLAYER_NAME));

        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        game.replayGame();

        vm.put("redPlayerName", player1.getPlayerName());
        vm.put("whitePlayerName", player2.getPlayerName());
        vm.put("viewMode", GetGameRoute.VIEW_MODE.PLAY.name());
        vm.put("activeColor", player1.getPlayerName().equals(playerName) ? "WHITE" : "RED");
        vm.put("currentPlayerName", playerName);
        vm.put("board", game.getBoard(player).getRaw());
        vm.put("replaySize", game.getCopyQueueSize());
        
        return templateEngine.render(new ModelAndView(vm, "replayGame.ftl"));

    }
}
