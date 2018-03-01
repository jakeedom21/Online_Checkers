package com.webcheckers.storage;

import com.webcheckers.model.Game;
import com.webcheckers.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qadirhaqq on 2/28/18.
 */
public class GameStorage {
    private HashMap<Integer, Game> storage = new HashMap<>();

    /**
     * Find Games by ID
     * @param id
     * @return game object
     */
    public Game find(int id) {
        return storage.get(id);
    }

    /**
     * Find Games by User
     * @param user
     */
    public ArrayList<Game> find(User user) {
        ArrayList<Game> userGames = new ArrayList<>();
        for(Map.Entry<Integer, Game> entry : storage.entrySet()) {
            Game game = entry.getValue();
            User[] players = game.getPlayers();
            if (players[0] == user)
                userGames.add(game);
            if (players[1] == user)
                userGames.add(game);
        }
        return userGames;
    }

    public Game createGame(User player, User opponent) {

    }

    public void endGame(int id) {

    }


}
