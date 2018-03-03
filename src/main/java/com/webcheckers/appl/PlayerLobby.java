package com.webcheckers.appl;

import com.webcheckers.model.Player;
import spark.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class PlayerLobby {
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());
// Constants
//    public final static String PLAYER = "player";
    private Map<String, Player> players = new HashMap<>();
    private List<String> users = new ArrayList<>();
    private Map<String, Session> sessionsByPlayer = new HashMap<>();
    private Map<String, String> playersbySession = new HashMap<>();

    public void addPlayer(String playerName, Session session){
        Player playerObject = new Player(playerName);
        players.put(playerName, playerObject);
        sessionsByPlayer .put(playerName, session);
        playersbySession.put(session.id(), playerName);
        users.add(playerName);
    }


    public boolean hasPlayer(String playerName){
            return players.containsKey(playerName);
    }
    public Session findSessionByPlayer(String playerName) { return sessionsByPlayer.get(playerName); }
    public Player getPlayerBySession(String sessionID) {
        String playerName = playersbySession.get(sessionID);
        return players.get(playerName);
    }
    public List<String> getPlayers(){
        return users;
    }
    public Player getPlayer(String playerName) {
        return players.get(playerName);
    }
}
