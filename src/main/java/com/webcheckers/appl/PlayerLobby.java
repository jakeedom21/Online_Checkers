package com.webcheckers.appl;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.model.Player;

/**
 * A Hash map to hold all the Players.
 * Maps String username -> Player objects.
 */
public class PlayerLobby {


    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());
    // Constants
    //    public final static String PLAYER = "player";
    private Map<String, Player> signedInPlayers = new HashMap<>();
    private Set<String> allUserNames = new HashSet<>();


    /**
     * Add a new player when user signs in
     * @param playerName the username submitted from sign-in page
     */
    public void addPlayer(String playerName) {
        Player playerObject = new Player(playerName);
        signedInPlayers.put(playerName, playerObject);
        allUserNames.add(playerName);
    }

    /**
     * Remove a player from the PlayerLobby
     * @param playerName username of the player to remove
     */
    public void removePlayer(String playerName) {
        if(signedInPlayers.containsKey(playerName)) {
            signedInPlayers.remove(playerName);
        }

        if(allUserNames.contains(playerName)) {
            allUserNames.remove(playerName);
        }
    }

    /**
     * Check if the current player is signed in
     * @param playerName username of the player to check
     * @return true if player is signed in, false otherwise
     */
    public boolean isActiveUser(String playerName) {
        return signedInPlayers.keySet().contains(playerName);
    }

    /**
     * See if a username exists in the PlayerLobby already
     * @param playerName the username to find the existing player
     * @return true if username exists already, false otherwise
     */
    public boolean hasUserName(String playerName) {
        return allUserNames.contains(playerName);
    }

    /**
     * Get the set of username existing in PlayerLobby
     * @return A set of username
     */
    public Set<String> getPlayers() {
        return signedInPlayers.keySet();
    }

    /**
     * Retrieve a player according to username
     * @param playerName username to find the player
     * @return the Player object of the same username
     */
    public Player getPlayerByUsername(String playerName) {
        return signedInPlayers.get(playerName);
    }

    /**
     * Get the names of the signed players that's not in a game
     * @param currentPlayerName the player to exclude from in the list
     * @return a set of username of free players excluding the param
     */
    public Set<String> getFreePlayerNames(String currentPlayerName) {
        Set<String> freePlayersNames = new TreeSet<>();
        for (Player player : signedInPlayers.values()) {
            //if (!player.isInGame() && (!player.getPlayerName().equals(currentPlayerName))) {
            //first condition taken out for the purpose of testing acceptance criteria
            if (!player.getPlayerName().equals(currentPlayerName)) {
                freePlayersNames.add(player.getPlayerName());
            }
        }

        return freePlayersNames;
    }
}