package com.webcheckers.appl;

/**
 * Created by qadirhaqq on 2/27/18.
 */

import com.webcheckers.controller.GameController;

import static spark.Spark.*;
import spark.Spark;

public class RouteManager {
    public static final String GAMES_ROUTE = "/game";
    public static final String SPECFIC_GAMES_ROUTE = GAMES_ROUTE + "/:id";

    public static GameController gameController;

    public RoutingEngine(GameController gameController) {
        this.gameController = gameController;

    }

    public void init() {
        Spark.post(GAMES_ROUTE, gameController::createNewGame);
        Spark.get(SPECFIC_GAMES_ROUTE, gameController::renderGamePage);
    }



}
