package com.webcheckers.appl;

/**
 * Created by qadirhaqq on 2/27/18.
 */

import com.webcheckers.ui.GetGameRoute;
import spark.Spark;

public class RouteManager {
    public static final String GAMES_ROUTE = "/game";
    public static final String HOME_ROUTE = "/home";
    public static final String SIGNIN_ROUTE = "/signin";
    public static final String SPECFIC_GAMES_ROUTE = GAMES_ROUTE + "/:id";

    public static GetGameRoute getGameRoute;
    public static PlayerLobby playerLobby;

    public RouteManager(GetGameRoute getGameRoute, PlayerLobby playerLobby) {
        this.getGameRoute = getGameRoute;
        this.playerLobby = playerLobby;

    }

    public void init() {
        Spark.post(GAMES_ROUTE, getGameRoute::renderGamePage);
    }

}
