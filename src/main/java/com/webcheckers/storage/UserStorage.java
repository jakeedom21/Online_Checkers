package com.webcheckers.storage;

import com.webcheckers.model.User;

import java.util.HashMap;

/**
 * Created by qadirhaqq on 2/28/18.
 */
public class UserStorage {

    HashMap<String, User> storage = new HashMap<>();

    public void addUser(User user) {
        storage.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return storage.get(username);
    }
}
