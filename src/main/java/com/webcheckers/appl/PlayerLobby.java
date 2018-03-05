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

/**
 * PlayerLobby is a class that contains player functions and holds player metadata.
 *
 * @version 1.0
 */
public class PlayerLobby {


    // Constants
    private Map<String, Player> signedInPlayers = new HashMap<>();
    private Set<String> allUserNames = new HashSet<>();

    /**
     * Initializes a Player object with the player's name
     * @param playerName
     */
    public void addPlayer(String playerName) {
        // Creates a player object
        Player playerObject = new Player(playerName);
        // Creates a new entry in the set of signed in players
        signedInPlayers.put(playerName, playerObject);
        // Adds the player's name to the user name pool of all player names
        allUserNames.add(playerName);
    }

    /**
     * Returns true on whether the player is active.
     * @param playerName
     * @return boolean
     */
    public boolean isActiveUser(String playerName) {
        return signedInPlayers.keySet().contains(playerName);
    }

    /**
     * Returns true if the player's name is in use.
     * @param playerName
     * @return boolean
     */
    public boolean hasUserName(String playerName) {
        return allUserNames.contains(playerName);
    }

    /**
     * Returns a set of signed in players
     * @return A set of player names
     */
    public Set<String> getPlayers() {
        return signedInPlayers.keySet();
    }

    /**
     * Getter for a Player object by the player's name.
     * @param playerName
     * @return Player object
     */
    public Player getPlayerByUsername(String playerName) {
        return signedInPlayers.get(playerName);
    }

    /**
     * Returns the set of players that are not in a game.
     * @param currentPlayerName
     * @return A set of player names
     */
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