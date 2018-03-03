package com.webcheckers.appl;

/**
 * Created by qadirhaqq on 2/27/18.
 */

import com.webcheckers.controller.GameController;
import spark.Spark;

public class RouteManager {
    public static final String GAMES_ROUTE = "/game";
    public static final String SPECFIC_GAMES_ROUTE = GAMES_ROUTE + "/:id";

    public static GameController gameController;
    public static PlayerLobby playerLobby;

    public RouteManager(GameController gameController, PlayerLobby playerLobby) {
        this.gameController = gameController;
        this.playerLobby = playerLobby;

    }

    public void init() {



        // Start Game
        Spark.post(GAMES_ROUTE, gameController::createNewGame);
        Spark.get(SPECFIC_GAMES_ROUTE, gameController::renderGamePage);
    }

}
