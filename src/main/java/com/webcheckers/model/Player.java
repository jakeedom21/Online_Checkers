package com.webcheckers.model;

public class Player {

    private enum PieceColor{
        RED, WHITE
    }

    private String playerName;
    private boolean inGame;
    private PieceColor pieceColor;


    /**
     * Constructor for Player
     *
     * @param playerName
     */
    public Player(String playerName){
        this.playerName = playerName;
        this.inGame = false;
        this.pieceColor = null;
    }

    public String getPlayerName(){
        return playerName;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
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
