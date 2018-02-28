package com.webcheckers.model;


public class Piece {

    private int row;
    private int col;
    private String color;
    private boolean isKing;

    /**
     * Constructor for a piece class
     * @param row - row number
     * @param col - col number
     * @param color - Color of the Piece
     */
    public Piece(int row, int col, String color) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.isKing = false;
    }

    /**
     * Sets the new row, column position of the Piece on the board
     * @param row - row number
     * @param col - col number
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row number of the Piece
     * @return row number
     */
    public int getRowNumber() {
        return row;
    }

    /**
     * Returns the col number of the Piece
     * @return col number
     */
    public int getColNumber() {
        return col;
    }

    /**
     * Returns the color of the Piece
     * @return piece color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the Piece as a King
     */
    public void setKing() {
        isKing = true;
    }

    /**
     * Returns whether the piece is a king or not
     * @return boolean
     */
    public boolean isKing() {
        return isKing;
    }

    public String toString() {
        return this.color;
    }
}

