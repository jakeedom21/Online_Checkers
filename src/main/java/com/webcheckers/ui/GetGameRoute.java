package com.webcheckers.ui;

/**
 * Created by Sameen Luo <xxl2398@rit.edu> on 2/28/2018.
 */

import com.webcheckers.storage.GameStorage;
import spark.Request;
import spark.Response;
import spark.Spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class GetGameRoute {

    public static final String GAME = "/game";
    public static final String GAME_ID = GAME + "/:id";
    private GameStorage gs;

    public String renderGamePage(Request request, Response response) {
        UserManager userManager = new UserManager(null, userDao, sessionDao, request);
        Optional<User> user = userManager.getLoggedInUser();

        if (!user.isPresent()) {
            // User is not logged in, redirect to the login page with a warning
            ViewUtil.setFlashMessage(request, new FlashMessage(FlashMessageType.WARNING, MUST_BE_LOGGED_IN_VIEW));
            response.redirect(RoutingEngine.LOGIN_ROUTE);
            return null;
        }

        User currentUser = user.get();

        // Make sure a valid game ID was passed
        final Integer gameId = Integer.parseInt(request.params(":id"));
        final Optional<Game> game = gs.find(gameId);
        if (!game.isPresent()) {
            // Invalid game
            ViewUtil.setFlashMessage(request, new FlashMessage(FlashMessageType.WARNING, INVALID_GAME));
            response.redirect(RoutingEngine.GAMES_ROUTE);
            return null;
        }

        Game gameObj = game.get();

        Map<String, Object> attributes = new HashMap<>();
        final List<User> players = gameObj.getPlayers();
        boolean readOnlyMode = false;

        // Get players
        final User player1 = gameObj.getPlayer1();
        final User player2 = gameObj.getPlayer2();

        // Has game been won?
        if (gameObj.getWinner().isPresent()) {
            readOnlyMode = true;

            User winner = gameObj.getWinner().get();
            attributes.put("winner", winner.getUsername());

            if (gameObj.didPlayerResign()) {
                if (winner.getUsername().equals(player1.getUsername())) {
                    attributes.put("resigned", player2.getUsername());
                } else {
                    attributes.put("resigned", player1.getUsername());
                }
            }

        }

        if (!players.contains(currentUser)) {
            readOnlyMode = true;
            attributes.put("title", String.format("Game #%d (%s vs. %s)", gameId, player1.getUsername(), player2.getUsername()));
        } else {
            User opponent = player2;
            String playerColor = GameBoard.PLAYER_1_PIECE_COLOR;
            String opponentColor = GameBoard.PLAYER_2_PIECE_COLOR;
            Boolean isMyTurn = gameObj.getCurrentTurn() == GameTurn.PLAYER1;

            if (currentUser.equals(player2)) {
                opponent = player1;
                playerColor = GameBoard.PLAYER_2_PIECE_COLOR;
                opponentColor = GameBoard.PLAYER_1_PIECE_COLOR;
                isMyTurn = gameObj.getCurrentTurn() == GameTurn.PLAYER2;
            }

            attributes.put("title", String.format("Game #%d (Opponent: %s)", gameId, opponent.getUsername()));
            attributes.put("playerColor", playerColor);
            attributes.put("opponentName", opponent.getUsername());
            attributes.put("opponentColor", opponentColor);
            attributes.put("isMyTurn", isMyTurn);
        }

        String whoseTurn;
        if (game.get().getCurrentTurn() == GameTurn.PLAYER1) {
            whoseTurn = player1.getUsername();
        } else {
            whoseTurn = player2.getUsername();
        }

        attributes.put("whoseTurn", whoseTurn);
        attributes.put("gameId", gameId);
        attributes.put("playerName", currentUser.getUsername());
        attributes.put("board", game.get().getBoard().getRaw());
        attributes.put("readOnly", readOnlyMode);
        attributes.put("apiRoot", RoutingEngine.GAMES_API_ROOT.replace(":id", gameObj.getId().toString()));

        return viewUtil.render(request, attributes, TPL_GAME);
    }

}


