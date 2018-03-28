package com.webcheckers.model;

public class Player {

    public enum PieceColor {
        RED, WHITE
    }

    private String playerName;
    private boolean inGame;
    private PieceColor pieceColor;
    private Game currentGame;
    private String opponentName;

    /**
     * Constructor for Player
     *
     * @param playerName
     */
    public Player(String playerName){
        this.playerName = playerName;
        this.inGame = false;
        this.pieceColor = null;
        this.currentGame = null;
        this.opponentName = null;
    }

    public String getPlayerName(){
        return playerName;
    }


    public String getOpponentName(){
        return opponentName;
    }


    public Game getGame() {
        return this.currentGame;
    }

    public boolean isInGame() {
        return inGame;
    }


    public PieceColor getPieceColor() {
        return pieceColor;
    }

    /**
     * Assign PieceColor and Game to this player who is getting put into a game
     * Called by Game.startGame
     * @param pieceColor assigned to this player, RED(1) and WHITE(2) for the playee
     * @param gameToPlay the Game object for this player and his/her opponent
     */
    public void assignGame(PieceColor pieceColor, Game gameToPlay, Player opponent) {
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
        this.currentGame = null;
        this.opponentName = null;
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
