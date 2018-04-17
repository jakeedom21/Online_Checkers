package com.webcheckers.model;

import java.io.Serializable;

/**
 * Represent the spaces on board to place pieces on.
 * Created by qadirhaqq on 2/27/18.
 */
public class Space implements Serializable{
    private int row;
    private int cell;
    private Piece piece = null;

    /**
     * Constructor for space object
     * @param row - row number
     * @param cell - cell number
     */
    public Space(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Returns the row of the space
     * @return int
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the space
     * @return int
     */
    public int getCol() {
        return cell;
    }

    /**
     * Sets a piece to a space
     * @param p - piece object
     */
    public void setPiece(Piece p) {
        piece = p;
        if (p != null)
            p.setPosition(this.row, this.cell);
    }

    /**
     * Returns piece object at space
     * @return piece object
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Determines if a space has a piece or not
     * @return boolean
     */
    public boolean hasPiece() {
        return this.piece != null;
    }

    /**
     * Returns true if a space is a valid space
     * @return boolean
     */
    public boolean isValid() { return true; }

    /**
     * Set coordinate of space after flipping
     * @param row - new row number
     * @param col - new col number
     */
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
