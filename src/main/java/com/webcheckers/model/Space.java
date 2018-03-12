package com.webcheckers.model;

import java.io.Serializable;

/**
 * Created by qadirhaqq on 2/27/18.
 */
public class Space implements Serializable{
    private int row;
    private int col;
    private Piece piece = null;
    private int MAX_DIM = 8;

    public Space(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPiece(Piece p) {
        piece = p;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isValid() {
        //if both col and row are even or odd is an invalid move
        if(this.getCol()%2 == 0 && this.getRow()%2 == 0){
            System.out.println("valid false");
            return false;
        }
        else if(this.getCol()%2 == 1 && this.getRow()%2 == 1){
            System.out.println("valid false");
            return false;
        }
        //if space has a piece already in it
        else if(this.hasPiece()){
            System.out.println("valid false");
            return false;
        }
        //not sure if actually needed as don't know if player actually can place outside board
        else if(this.getRow() < 0 || this.getCol() < 0){
            System.out.println("valid false");
            return false;
        }
        //for this in particular, if player can place outside board will need a get in board for MAX_DIM
        else if(this.getCol() >= MAX_DIM || this.getRow() >= MAX_DIM){
            System.out.println("valid false");
            return false;
        }
        else{
            System.out.println("valid true");
            return true;
        }
    }

    public boolean hasPiece() {
        return this.piece != null;
    }

    public String toString() {
        String returnString = "|";
        returnString += this.piece == null ?  " " : piece.toString();
        returnString += "|";
        return returnString;
    }
}
