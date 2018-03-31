package com.webcheckers.ui;

/**
 * The UI Controller to GET the Game page.
 *
 * Created by Sameen Luo <xxl2398@rit.edu> on 2/28/2018.
 */

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.webcheckers.utils.Constants.*;

public class GetGameRoute implements Route {

    private static final String GAME_FTL = "game.ftl";
    final static String GAME_ATTR = "game";
    PlayerLobby playerLobby;
    TemplateEngine templateEngine;

    public enum VIEW_MODE {
        PLAY, REPLAY, SPECTATOR
    }

    private static int gameId = 0;
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    public GetGameRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    private boolean busyOpponent(String opponentName) {
        return playerLobby.getPlayerByUsername(opponentName).isInGame();
    }

    public String renderGamePage(Request request, Response response) {

        Session currentSession = request.session();
        String currentPlayerName = currentSession.attribute("playerName");
        Player currentPlayer = playerLobby.getPlayerByUsername(currentPlayerName);
        if (currentPlayer == null) {
            response.redirect("/");   //TODO: refactor constant Strings for urls
            return null;
        }

        Game game;
        Player opponent;

        String opponentName = request.queryParams("opponentName");

        // if player chose opponent successfully, opponentName is not null
        if (opponentName != null) {
            opponent = playerLobby.getPlayerByUsername(opponentName);

            //busyOpponentError, back to /home choose opponent again
            if (busyOpponent(opponentName)) {

                currentSession.attribute(BUSY_OPPONENT_ERROR, true);
                response.redirect(HOME_URL);
                return null;
            } else {
                currentSession.attribute(BUSY_OPPONENT_ERROR, false);
            }
            game = new Game(gameId, currentPlayer, opponent);
            // if player did not choose opponent (assigned a game),
            // opponentName is null(?) since there's no request came from /home
        } else {
            //opponent = playerLobby.getPlayerByUsername(currentPlayer.getOpponentName());
            game = currentPlayer.getGame();
        }


        Map<String, Object> attributes = new HashMap<>();
        attributes.put(SIGNED_IN_PLAYER, playerLobby.isActiveUser(currentPlayer.getPlayerName()));
        attributes.put(PLAYER_NAME, currentSession.attribute(PLAYER_NAME));


        // Get players
        final Player player1 = game.getPlayer1();
        final Player player2 = game.getPlayer2();

        // see if opponent has resigned the game
        if (game.didPlayerResign()) {
            currentPlayer.finishGame();
            currentSession.attribute(GAME_WON, true);
            currentSession.attribute(OPPONENT_FORFEIT, true);
            //currentSession.attribute(BUSY_OPPONENT_ERROR, false);
            response.redirect(RESULT_URL);

            return null;
        }

        // Has game been won?
        if (game.isGameWon()) {
            String winner = game.getWinner();
            attributes.put("winner", winner);

            if (game.didPlayerResign()) {
                if (winner.equals(player1.getPlayerName())) {
                    attributes.put("resigned", player2.getPlayerName());
                } else {
                    attributes.put("resigned", player1.getPlayerName());  //TODO: make use of attribute map to redirect to forfeit result page
                }
            }
        }

        if (player1 != currentPlayer && player2 != currentPlayer) {
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
                isMyTurn = game.getPlayerTurn().equals(currentPlayerName);
            }

            attributes.put("title", String.format("Game #%d (Opponent: %s)", gameId, opponent.getPlayerName()));
            attributes.put("playerColor", playerColor);
            attributes.put("name", opponent.getPlayerName());
            attributes.put("opponentColor", opponentColor);
            attributes.put("isMyTurn", isMyTurn);
        }


        String whoseTurn =  game.getPlayerTurn();
        String viewMode = whoseTurn.equals(currentPlayerName) ? VIEW_MODE.PLAY.name() : VIEW_MODE.SPECTATOR.name();

        Player.PieceColor activeColor = player1.getPlayerName().equals(whoseTurn)
                ? Player.PieceColor.RED : Player.PieceColor.WHITE ;


        attributes.put("redPlayerName", player1.getPlayerName());
        attributes.put("whitePlayerName", player2.getPlayerName());
        attributes.put("viewMode", viewMode);
        attributes.put("activeColor", activeColor == Player.PieceColor.RED ? "RED" : "WHITE");
        attributes.put("currentPlayerName", currentPlayerName);
        attributes.put("board", game.getBoard(currentPlayer).getRaw());

        return templateEngine.render(new ModelAndView(attributes, GAME_FTL));
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetGameRoute is invoked");
        return renderGamePage(request, response);
    }
}


