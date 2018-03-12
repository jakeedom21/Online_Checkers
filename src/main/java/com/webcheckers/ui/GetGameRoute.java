package com.webcheckers.ui;

/**
 * Created by Sameen Luo <xxl2398@rit.edu> on 2/28/2018.
 */
 
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.storage.SessionStorage;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class GetGameRoute implements Route {

    public static final String GAME = "/game";
    private static final String GAME_FTL = "game.ftl";
    final static String GAME_ATTR = "game";
    PlayerLobby playerLobby;
    SessionStorage sessionStorage;
    TemplateEngine templateEngine;

    public enum VIEW_MODE {
        PLAY, REPLAY, SPECTATOR
    }

    private static int gameId = 0;
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    public GetGameRoute(final PlayerLobby playerLobby, SessionStorage sessionStorage, final TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.sessionStorage = sessionStorage;
        this.templateEngine = templateEngine;
    }

    private boolean busyOpponent(String opponentName) {
        return playerLobby.getPlayerByUsername(opponentName).isInGame();
    }

    public String renderGamePage(Request request, Response response) {

        Session currentSession = request.session();
        Player currentPlayer = sessionStorage.getPlayerBySession(currentSession.id());
        if (currentPlayer == null) {
            sessionStorage.debugPrint();
        }

        Game game;
        Player opponent;

        String opponentName = request.queryParams("opponentName");

        // if player chose opponent successfully, opponentName is not null
        if (opponentName != null) {
            opponent = playerLobby.getPlayerByUsername(opponentName);

            //busyOpponentError, back to /home choose opponent again
            if (busyOpponent(opponentName)) {
                currentSession.attribute(GetHomeRoute.BUSY_OPPONENT_ATTR, true);
                response.redirect("/");
            } else {
                currentSession.attribute(GetHomeRoute.BUSY_OPPONENT_ATTR, false);
            }
            game = new Game(gameId, currentPlayer, opponent);

        // if player did not choose opponent (assigned a game),
        // opponentName is null(?) since there's no request came from /home
        } else {
            //opponent = playerLobby.getPlayerByUsername(currentPlayer.getOpponentName());
            game = currentPlayer.getGame();
        }

        //set pieces' orientation according the session's owner
        game.setOrientation(currentPlayer);


        // save game in session attribute map
        currentSession.attribute(GAME_ATTR,game);

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(GetHomeRoute.SIGNED_IN_ATTR, playerLobby.isActiveUser(currentPlayer.getPlayerName()));
        attributes.put(PostSignInRoute.PLAYER_NAME_ATTR, currentSession.attribute(PostSignInRoute.PLAYER_NAME_ATTR));
        boolean viewMode = false;

        // Get players
        final Player player1 = game.getPlayers()[0];
        final Player player2 = game.getPlayers()[1];

        // Has game been won?
        if (game.getWinner() != null) {
            viewMode = true;

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
            viewMode = true;
            attributes.put("title", String.format("Game #%d (%s vs. %s)", gameId, player1.getPlayerName(), player2.getPlayerName()));
        } else {
            opponent = player2;
            String playerColor = currentPlayer.getPieceColor() == Player.PieceColor.RED ? "RED" : "WHITE";
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
            attributes.put("name", opponent.getPlayerName());
            attributes.put("opponentColor", opponentColor);
            attributes.put("isMyTurn", isMyTurn);
        }

        Player whoseTurn;
        if (game.getPlayerTurn().equals(game.getPlayers()[0].getPlayerName())) {
            whoseTurn = player1;
        } else {
            whoseTurn = player2;
        }

        attributes.put("redPlayerName", player1.getPlayerName());
        attributes.put("whitePlayerName", player2.getPlayerName());
        attributes.put("viewMode", VIEW_MODE.PLAY.name());
        attributes.put("activeColor", "RED");
        attributes.put("currentPlayerName", whoseTurn.getPlayerName());
        attributes.put("board", game.getBoard().getRaw());

        return templateEngine.render(new ModelAndView(attributes, GAME_FTL));
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetGameRoute is invoked");
        return renderGamePage(request, response);
    }
}


