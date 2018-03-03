package com.webcheckers.storage;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Session;

import java.util.HashMap;

/**
 * Created by qadirhaqq on 3/3/18.
 */
public class SessionStorage {

    PlayerLobby playerLobby;
    HashMap<String, Session> playersBySession = new HashMap<>();
    HashMap<String, String> sessionsByPlayer = new HashMap<>();

    public SessionStorage(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    public void addSession(String playerUsername, Session session) {
        playersBySession.put(playerUsername, session);
        sessionsByPlayer.put(session.id(), playerUsername);
    }

    public Session getSessionByUsername(String playerUsername) {
        return playersBySession.get(playerUsername);
    }

    public Player getPlayerBySession(String sessionId) {
        String playerName = sessionsByPlayer.get(sessionId);
        return playerLobby.getPlayerByUsername(playerName);
    }


}
