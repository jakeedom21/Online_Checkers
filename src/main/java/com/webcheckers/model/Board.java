package com.webcheckers.model;

import com.webcheckers.utils.Constants;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by qadirhaqq on 2/27/18.
 */
public class Board implements Serializable{
    private int redPieces = 12;
    private int whitePieces = 12;
    private static final int MAX_DIM = 8;
    private static final String PLAYER1_COLOR = "W";
    private static final String PLAYER2_COLOR = "R";
    private static HashSet<Integer> EMPTY_ROWS = new HashSet<>();
    private Space[][] board = new Space[MAX_DIM][MAX_DIM];
    
    /**
     * Constructor for board object
     * //Picture used as reference: http://allaboutfunandgames.com/wp-content/uploads/2011/11/Checkers.jpg
     */
    public Board() {
        //Create each space
        for (int i = 0; i < MAX_DIM; i++) {
            for (int j = 0; j < MAX_DIM; j++) {
                board[i][j] = new Space(i, j);
            }
        }
        setBoardPieces();
    }

    // copy constructor
    public Board(Board b) {
        for (int i = 0; i < MAX_DIM; i++) {
            for (int j = 0; j < MAX_DIM; j++) {
                board[i][j] = new Space(i, j);
            }
        }
        for (int i = 0; i < MAX_DIM; i++) {
            for (int j = 0; j < MAX_DIM; j++) {
                if (b.getSpace(i, j).getPiece() != null){
                    Piece p = b.getSpace(i, j).getPiece();
                    board[i][j].setPiece(p);
                }
            }
        }
    }

    /**
     * Sets the board pieces for the initial start to a game
     */
     public void setBoardPieces(){
        EMPTY_ROWS.add(3);
        EMPTY_ROWS.add(4);
        // Populate the board from top to bottom
        for (int row = 0; row < MAX_DIM; row++) {
            if (!EMPTY_ROWS.contains(row)) { // not rows 3 or 4
                if (row % 2 == 0) {
                    for (int col = 1; col < MAX_DIM; col += 2) {
                        createPiece(row, col, Constants.PieceColor.RED);
                    }
                }
                if (row % 2 == 1) {
                    for (int col = 0; col <  MAX_DIM; col += 2) {
                        createPiece(row, col, Constants.PieceColor.RED);
                    }
                }
            }
        }
    }

    /**
     * Initializes a piece on the board
     * @param row - row number
     * @param col - column number
     * @param pieceColor - piece color
     */
    private void createPiece(int row, int col, Constants.PieceColor pieceColor) {
        String color;
        if (pieceColor.equals(Constants.PieceColor.WHITE)) {
            color = (row > MAX_DIM / 2) ? PLAYER1_COLOR : PLAYER2_COLOR;
        } else { //RED
            color = (row > MAX_DIM / 2) ? PLAYER2_COLOR : PLAYER1_COLOR;
        }
        board[row][col].setPiece(new Piece(row, col, color));
    }

    public String toString() {
        String returnString = "\n------------------------\n";
        for(int i = 0; i < MAX_DIM; i++) {
            for (int j = 0; j < MAX_DIM; j++) {
                returnString += board[i][j];
            }
            returnString += "\n------------------------\n";
        }
        return returnString;
    }

    /**
     * Moves a piece on the board
     * @param start - start space
     * @param end - end space
     */
    public void movePiece(Space start, Space end) {
         int r = start.getRow();
         int c = start.getCol();
         Piece piece = board[r][c].getPiece();
         board[r][c].setPiece(null);
         r = end.getRow();
         c = end.getCol();
         if (r == 0 && !piece.isKing()) {
             piece.setKing();
         }
         board[r][c].setPiece(piece);
    }

    /**
     * Returns a space on the board
     * @param row - row number
     * @param col - column number
     * @return space object
     */
    public Space getSpace(int row, int col) {
        return board[row][col];
    }

    /**
     * Returns number of red pieces
     * @return int
     */
    public int getP1Pieces() {
        return this.redPieces;
    }

    /**
     * Returns number of white pieces
     * @return int
     */
    public int getP2Pieces() {
        return this.whitePieces;
    }

    /**
     * Returns a raw two-d array representation of the board
     * @return two-d array of spaces
     */
    public Space[][] getRaw() {
        return this.board;
    }

    /**
     * Rotates the board and sets every row/col to it's new location
     */
    public void flip() { // Rotates board 90 degrees twice
        int n = board.length;
        for (int i = 0; i < 2; i++) {
            for (int l = 0; l < n/2; l++) {
                for(int c = l; c < n-l-1; c++) {
                    Space top = board[l][c];
                    board[l][c] = board[n-1-c][l];
                    board[n-1-c][l] = board[n-l-1][n-c-1];
                    board[n-l-1][n-c-1] = board[c][n-l-1];
                    board[c][n-l-1] = top;
                }
            }
        }
        for (int i = 0; i < Constants.MAX_DIM; i++) {
            for (int j = 0; j < Constants.MAX_DIM; j++) {
                board[i][j].setCoor(i, j);
            }
        }
    }

    /**
     * Removes a piece from the board after an opponent jumped it
     * @param removePiece - the piece being removed
     */
    public void removePiece(Space removePiece){
         int row = removePiece.getRow();
         int col = removePiece.getCol();
         if(board[row][col].hasPiece()){
             if(board[row][col].getPiece().getColor().equals("WHITE")){
                 this.whitePieces--;//remove 1 white piece
             }
             else{
                 this.redPieces--;//removes 1 red piece
             }
         }
         board[row][col].setPiece(null);//removes piece from board
    }
}

