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


    public void addPlayer(String playerName) {
        Player playerObject = new Player(playerName);
        signedInPlayers.put(playerName, playerObject);
        allUserNames.add(playerName);
    }

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

    public boolean hasUserName(String playerName) {
        return allUserNames.contains(playerName);
    }

    public Set<String> getPlayers() {
        return signedInPlayers.keySet();
    }

    public Player getPlayerByUsername(String playerName) {
        return signedInPlayers.get(playerName);
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