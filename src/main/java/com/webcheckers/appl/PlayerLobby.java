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
    //    public final static String PLAYER = "player";
    private Map<String, Player> signedInPlayers = new HashMap<>();
    private Set<String> allUserNames = new HashSet<>();


    public void addPlayer(String playerName) {
        Player playerObject = new Player(playerName);
        signedInPlayers.put(playerName, playerObject);
        allUserNames.add(playerName);
    }

    public boolean isActiveUser(String playerName) {
        return signedInPlayers.keySet().contains(playerName);
    }

    public boolean hasUserName(String playerName) {
        return allUserNames.contains(playerName);
    }

    public Set<String> getPlayers() {
        return signedInPlayers.keySet();
    }

    public Set<String> getFreePlayerNames(String currentPlayerName) {
        Set<String> freePlayersNames = new TreeSet<>();
        for (Player player : signedInPlayers.values()) {
            if (!player.isInGame() && (!player.getPlayerName().equals(currentPlayerName))) {
                freePlayersNames.add(player.getPlayerName());
            }
        }

        return freePlayersNames;
    }
}