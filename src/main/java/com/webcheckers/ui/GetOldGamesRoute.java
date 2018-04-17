package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.utils.Constants;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class GetOldGamesRoute implements Route {

    TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    /**
     * Constructor for the oldGames route
     * @param playerLobby - playerLobby object
     * @param templateEngine - template Engine object
     */
    public GetOldGamesRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    /**
     * Handle the server request for a "oldGames" link
     * @param request - spark request
     * @param response - spark response
     * @return String of the rendered ".ftl"
     */
    @Override
    public Object handle(Request request, Response response) {
        Session currentSession = request.session();
        String playerName = currentSession.attribute(Constants.PLAYER_NAME);
        Player player = playerLobby.getPlayerByUsername(playerName);

        Map<String, Object> vm = new HashMap<>();
        int namesLen = player.getOldGames().size();
        String[] names = new String[namesLen];

        for (int i = 0; i < namesLen; i++) {
            Game game = player.getOldGames().get(i);
            game.copyReplayIntoQueue();
            game.resetBoard();
            String player1Name = game.getPlayer1().getPlayerName();
            String player2Name = game.getPlayer2().getPlayerName();
            names[i] = playerName.equals(player1Name) ?  player2Name : player1Name;
        }

        vm.put("oldOpponentGames", names);
        vm.put("playerName", playerName);

        return templateEngine.render(new ModelAndView(vm, "oldGames.ftl"));
    }
}
