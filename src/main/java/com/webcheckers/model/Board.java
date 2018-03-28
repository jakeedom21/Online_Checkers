package com.webcheckers.model;

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

    //Picture used as reference: http://allaboutfunandgames.com/wp-content/uploads/2011/11/Checkers.jpg
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
                    String color = b.getSpace(i, j).getPiece().getColor();
                    board[i][j].setPiece(new Piece(i, j, color.equals("WHITE") ? "W" : "R"));
                }

            }
        }
    }

    /**
     * Call when want to flip the board orientation by passing in the owner of the session
     */
     public void setBoardPieces(){
        EMPTY_ROWS.add(3);
        EMPTY_ROWS.add(4);
        // Populate the board from top to bottom
        for (int row = 0; row < MAX_DIM; row++) {
            if (!EMPTY_ROWS.contains(row)) { // not rows 3 or 4
                if (row % 2 == 0) {
                    for (int col = 1; col < MAX_DIM; col += 2) {
                        createPiece(row, col, Player.PieceColor.RED);
                    }
                }
                if (row % 2 == 1) {
                    for (int col = 0; col <  MAX_DIM; col += 2) {
                        createPiece(row, col, Player.PieceColor.RED);
                    }
                }
            }
        }
    }

    private void createPiece(int row, int col, Player.PieceColor pieceColor) {
        String color;
        if (pieceColor.equals(Player.PieceColor.WHITE)) {
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

    public void movePiece(Space start, Space end) {
        int r = start.getRow();
        int c = start.getCol();
        Piece piece = board[r][c].getPiece();
        board[r][c].setPiece(null);
        r = end.getRow();
        c = end.getCol();
        board[r][c].setPiece(piece);
    }

    public Space getSpace(int row, int col) {
        return board[row][col];
    }

    public int getP1Pieces() {
        return this.redPieces;
    }

    public int getP2Pieces() {
        return this.whitePieces;
    }

    public Space[][] getRaw() {
        return this.board;
    }

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
        for (int i = 0; i < MAX_DIM; i++) {
            for (int j = 0; j < MAX_DIM; j++) {
                board[i][j].setCoor(i, j);
            }
        }
    }


}

