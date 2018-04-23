package com.webcheckers.ui;

/**
 * The UI Controller to GET the Game page.
 * Created by Sameen Luo <xxl2398@rit.edu> on 2/28/2018.
 */

import com.webcheckers.appl.MoveValidation;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Space;
import com.webcheckers.utils.Constants;
import spark.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Logger;

import static com.webcheckers.utils.Constants.*;

/**
 * The UI Controller to GET the Game page.
 */
public class GetGameRoute implements Route {

    private static final String GAME_FTL = "game.ftl";
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    public enum VIEW_MODE {
        PLAY, REPLAY, SPECTATOR
    }

    private static int gameId = 0;
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    /**
     * GetGameRoute Constructor
     * @param playerLobby - instantiated playerLobby
     * @param templateEngine - instantiated templateEngine
     */
    public GetGameRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Determines if an opponent is in game or not
     * @param opponentName - opponent name from queryParams
     * @return boolean
     */
    private boolean busyOpponent(String opponentName) {
        return playerLobby.getPlayerByUsername(opponentName).isInGame();
    }

    /**
     * Route for rendering game page view
     * @param request - spark request
     * @param response - spark response
     * @return templateEngine view Model as String
     */
    public String renderGamePage(Request request, Response response) {

        Session currentSession = request.session();
        String currentPlayerName = currentSession.attribute("playerName");
        Player currentPlayer = playerLobby.getPlayerByUsername(currentPlayerName);
        if (currentPlayer == null) {
            response.redirect(HOME_URL);
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
            currentSession.attribute(BUSY_OPPONENT_ERROR, false);
            response.redirect(RESULT_URL);
            return null;    //stop rendering game page
        }

        // Has game been won?
        if (game.isGameWon()) {
            String winner = game.getWinner();
            attributes.put("winner", winner);
            if (winner.equals(currentSession.attribute(PLAYER_NAME))){
                currentSession.attribute(GAME_WON, true);
                currentSession.attribute(OPPONENT_FORFEIT, false);
            } else {
                currentSession.attribute(GAME_WON,false);
                currentSession.attribute(OPPONENT_FORFEIT, false);
            }
            response.redirect(RESULT_URL);
            return null;
        }

        if (player1 != currentPlayer && player2 != currentPlayer) {
            attributes.put("title", String.format("Game #%d (%s vs. %s)", gameId, player1.getPlayerName(), player2.getPlayerName()));
        } else {
            opponent = player2;
            String playerColor = currentPlayer.getPieceColor() == PieceColor.RED ? "RED" : "WHITE";
            String opponentColor = playerColor.equals("RED") ? "WHITE" : "RED";
            Boolean isMyTurn = game.getPlayerTurn().equals(player1.getPlayerName());

            if (currentPlayer.equals(player2)) {
                opponent = player1;
                playerColor = currentPlayer.getPieceColor() == PieceColor.WHITE ? "WHITE" : "RED";
                opponentColor = playerColor.equals("WHITE") ? "WHITE" : "RED";
                isMyTurn = game.getPlayerTurn().equals(currentPlayerName);
            }

            HashMap<Space, Space> map = MoveValidation.moveJumpList(playerColor, game.getBoard(currentPlayer));
            String[] translate = new String[map.size()];
            Iterator it = map.entrySet().iterator();
            int count = 0;
            while(it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                Space place = ((Space) pair.getKey());
                Space end = ((Space) pair.getValue());
                String entry = "Piece at " + place.getRow() + ", " + place.getCol() + " can go to " + end.getRow() + ", " + end.getCol();
                translate[count] = entry;
                count++;
            }
            //list for the spaces and possible moves

            attributes.put("spaceMoveList", translate);
            attributes.put("title", String.format("Game #%d (Opponent: %s)", gameId, opponent.getPlayerName()));
            attributes.put("playerColor", playerColor);
            attributes.put("name", opponent.getPlayerName());
            attributes.put("opponentColor", opponentColor);
            attributes.put("isMyTurn", isMyTurn);

        }

        String whoseTurn =  game.getPlayerTurn();

        Constants.PieceColor activeColor = player1.getPlayerName().equals(whoseTurn)
                ? Constants.PieceColor.RED : Constants.PieceColor.WHITE ;


        attributes.put("redPlayerName", player1.getPlayerName());
        attributes.put("whitePlayerName", player2.getPlayerName());
        attributes.put("viewMode", VIEW_MODE.PLAY.name());
        attributes.put("activeColor", activeColor == Constants.PieceColor.RED ? "RED" : "WHITE");
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


