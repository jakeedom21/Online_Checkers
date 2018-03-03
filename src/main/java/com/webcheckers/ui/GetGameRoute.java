package com.webcheckers.ui;

/**
 * Created by Sameen Luo <xxl2398@rit.edu> on 2/28/2018.
 */

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.RouteManager;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.storage.SessionStorage;
import spark.*;

import java.util.HashMap;
import java.util.Map;


public class GetGameRoute implements Route {

    public static final String GAME = "/game";
    private static final String GAME_FTL = "game.ftl";
    PlayerLobby playerLobby;
    SessionStorage sessionStorage;
    TemplateEngine templateEngine;
    private static int gameId = 0;

    public GetGameRoute(final PlayerLobby playerLobby, SessionStorage sessionStorage, final TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.sessionStorage = sessionStorage;
        this.templateEngine = templateEngine;
    }

    public String createNewGame(Request request, Response response) {
        // initialize new game
        Player p1 = sessionStorage.getPlayerBySession(request.session().id());
        if (p1 == null){
            // redirect
        }
        final String opponentName = request.queryParams("opponentName");
        Player p2 = playerLobby.getPlayerByUsername(opponentName);

        // createNewGame
        //Game.startNewGame(gameId, p1, p2);
        response.redirect(RouteManager.GAMES_ROUTE);
        return null;
    }

    public String renderGamePage(Request request, Response response) {

        Player currentPlayer = sessionStorage.getPlayerBySession(request.session().id());


        Game game = currentPlayer.getGame();

        Map<String, Object> attributes = new HashMap<>();
        boolean readOnlyMode = false;

        // Get players
        final Player player1 = game.getPlayers()[0];
        final Player player2 = game.getPlayers()[1];

        //Game.startNewGame(gameId, player1, player2);

        // Has game been won?
        if (game.getWinner() != null) {
            readOnlyMode = true;

            String winner = game.getWinner();
            attributes.put("winner", winner);

            if (game.didPlayerResign()) {
                if (winner.equals(player1.getPlayerName())) {
                    attributes.put("resigned", player2.getPlayerName());
                } else {
                    attributes.put("resigned", player1.getPlayerName());
                }
            }

        }

        if (player1 != currentPlayer && player2 != currentPlayer) {
            readOnlyMode = true;
            attributes.put("title", String.format("Game #%d (%s vs. %s)", gameId, player1.getPlayerName(), player2.getPlayerName()));
        } else {
            Player opponent = player2;
            String playerColor = currentPlayer.getPieceColor() == Player.PieceColor.RED ? "RED" : "WHITE" ;
            String opponentColor = playerColor.equals("RED") ? "WHITE" : "RED";
            Boolean isMyTurn = game.getPlayerTurn().equals(player1.getPlayerName());

            if (currentPlayer.equals(player2)) {
                opponent = player1;
                playerColor = currentPlayer.getPieceColor() == Player.PieceColor.WHITE ? "WHITE" : "RED";
                opponentColor = playerColor.equals("WHITE") ? "WHITE" : "RED";
                isMyTurn = game.getPlayerTurn().equals(player2.getPlayerName());
            }

            attributes.put("title", String.format("Game #%d (Opponent: %s)", gameId, opponent.getPlayerName()));
            attributes.put("playerColor", playerColor);
            attributes.put("opponentName", opponent.getPlayerName());
            attributes.put("opponentColor", opponentColor);
            attributes.put("isMyTurn", isMyTurn);
        }

        String whoseTurn;
        if (game.getPlayerTurn().equals(game.getPlayers()[0].getPlayerName())) {
            whoseTurn = player1.getPlayerName();
        } else {
            whoseTurn = player2.getPlayerName();
        }

        attributes.put("whoseTurn", whoseTurn);
        attributes.put("gameId", gameId);
        attributes.put("playerName", currentPlayer.getPlayerName());
        attributes.put("board", game.getBoard());
        attributes.put("readOnly", readOnlyMode);

        return templateEngine.render(new ModelAndView(attributes, GAME_FTL));
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        return renderGamePage(request, response);
    }
}


