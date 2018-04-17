package com.webcheckers.model;

import com.webcheckers.utils.Constants;

import java.util.ArrayList;

/**
 * Represent each signed in user
 */
public class Player {


    private String playerName;
    private boolean inGame;
    private Constants.PieceColor pieceColor;
    private Game currentGame;
    private String opponentName;
    private ArrayList<Game> oldGames = new ArrayList<>();

    /**
     * Constructor for Player
     *
     * @param playerName username of this player
     */
    public Player(String playerName){
        this.playerName = playerName;
        this.inGame = false;
        this.pieceColor = null;
        this.currentGame = null;
        this.opponentName = null;
    }

    /**
     * Get the username of this player
     * @return username
     */
    public String getPlayerName(){
        return playerName;
    }


    /**
     * Get the opponent's username of this Player
     * @return opponent's username
     */
    public String getOpponentName(){
        return opponentName;
    }

    /**
     * pre: Player is in game
     * Get the game this Player is currently in
     * @return The current game, null if not currently in game
     */
    public Game getGame() {
        return this.currentGame;
    }

    /**
     * Check if this player is in a game
     * @return true is in game, false otherwise
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Get the color of this player
     * @return enum RED or WHITE
     */
    public Constants.PieceColor getPieceColor() {
        return pieceColor;
    }

    /**
     * Assign PieceColor and Game to this player who is getting put into a game
     * Called by Game.startGame
     * @param pieceColor assigned to this player, RED(1) and WHITE(2) for the playee
     * @param gameToPlay the Game object for this player and his/her opponent
     */
    public void assignGame(Constants.PieceColor pieceColor, Game gameToPlay, Player opponent) {
        this.inGame = true;
        this.pieceColor = pieceColor;
        this.currentGame = gameToPlay;
        this.opponentName = opponent.getOpponentName();
    }

    /**
     * Called when a game is finished to set the correct fields for this player
     */
    public void finishGame(){
        this.inGame = false;
        this.pieceColor = null;
        this.oldGames.add(currentGame);
        this.currentGame = null;
        this.opponentName = null;
    }

    /**
     * Get old games for a player
     * @return ArrayList object
     */
    public ArrayList<Game> getOldGames() {
        return this.oldGames;
    }

    /**
     * Check equality on only playerName field because it's the only immutable field
     * @param playerObject the other object to compare to
     * @return true if equal, false otherwise
     */
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

    /**
     * hash on only playerName field because it's the only immutable field
     * @return hashcode of this player
     */
    @Override
    public int hashCode(){
        return playerName.hashCode();
    }

}
