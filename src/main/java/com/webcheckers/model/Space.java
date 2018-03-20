package com.webcheckers.model;

import java.io.Serializable;
import com.webcheckers.appl.MoveValidation;

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
        return MoveValidation.validPlacement(this);
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
