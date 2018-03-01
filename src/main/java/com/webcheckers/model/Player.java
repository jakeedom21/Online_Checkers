package com.webcheckers.model;

public class Player {
    private String playerName;

    /**
     * Constructor for Player
     *
     * @param playerName
     */
    public Player(String playerName){
        this.playerName = playerName;
    }
    public String getPlayerName(){
        return playerName;
    }

    @Override
    public boolean equals(Object playerObject){
        if(playerObject == null) {
            return false;
        } else if (playerObject instanceof Player){
            if (((Player) playerObject).getPlayerName().equals(this.playerName)){
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode(){
        return playerName.hashCode();
    }

}
