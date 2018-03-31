package com.webcheckers.model;

import java.io.Serializable;

/**
 * Created by qadirhaqq on 2/27/18.
 */
public class Space implements Serializable{
    private int row;
    private int cell;

    private Piece piece = null;
    private int MAX_DIM = 8;

    public Space(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return cell;
    }

    public void setPiece(Piece p) {
        piece = p;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece() {
        return this.piece != null;
    }

    public boolean isValid() { return true; }

    public void setCoor(int row, int col ) {
        this.row = row;
        this.cell = col;
        if (this.piece != null) {
            this.piece.setPosition(row, col);
        }
    }

    public String toString() {
        String returnString = "|";
        returnString += this.piece == null ?  " " : piece.toString();
        returnString += "|";
        return returnString;
    }
}
