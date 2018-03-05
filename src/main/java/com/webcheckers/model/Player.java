package com.webcheckers.model;

/**
 * This class holds Player metadata and functions to modify and get player values.
 *
 * @version 1.0
 */
public class Player {

    // Constants
    public enum PieceColor {
        RED, WHITE
    }

    private String playerName;
    private String name;
    private boolean inGame;
    private PieceColor pieceColor;
    private Game currentGame;

    /**
     * Constructor for Player
     *
     * @param playerName
     */
    public Player(String playerName){
        this.playerName = playerName;
        this.name = playerName;
        this.inGame = false;
        this.pieceColor = null;
        this.currentGame = null;
    }

    /**
     * Getter for the players name
     * @return the player's name
     */
    public String getPlayerName(){
        return playerName;
    }

    /**
     * Setter for the player's game
     * @param game
     */
    public void setGame(Game game){
        this.currentGame = game;
    }

    /**
     * Getter for the player's game
     * @return
     */
    public Game getGame() {
        return this.currentGame;
    }

    /**
     * Function to identify if a player is in a game
     * @return boolean: True if player is in a game
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Setter for the boolean for whether a player is in a game
     * @param inGame
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Getter for the player's piece color
     * @return red or white piece color
     */
    public PieceColor getPieceColor() {
        return pieceColor;
    }

    /**
     * Assign PieceColor and Game to this player who is getting put into a game
     * Called by Game.startGame
     * @param pieceColor assigned to this player, RED(1) and WHITE(2) for the playee
     * @param gameToPlay the Game object for this player and his/her opponent
     */
    public void assignGame(PieceColor pieceColor, Game gameToPlay) {
        this.inGame = true;
        this.pieceColor = pieceColor;
        this.currentGame = gameToPlay;
    }

    /**
     * Called when a game is finished to set the correct fields for this player
     */
    public void finishGame(){
        this.inGame = false;
        this.pieceColor = null;
        this.currentGame = null;
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
