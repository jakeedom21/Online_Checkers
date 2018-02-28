package com.webcheckers.appl;

import java.util.*;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Route;
import spark.TemplateEngine;
import com.webcheckers.model.Player;


public class PlayerLobby {
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());
// Constants
    public final static String PLAYER = "player";
    private Map<String, Player> players = new HashMap<>();
    private List<String> users = new ArrayList<>();

    public void addPlayer(Session session, String playerName){
            session.attribute(PLAYER, new Player(playerName));
            players.put(playerName, session.attribute(PLAYER));
            users.add(playerName);
    }
    public boolean hasPlayer(String playerName){
            return players.containsKey(playerName);
    }
    public List<String> getPlayers(){
        return users;
    }
}
