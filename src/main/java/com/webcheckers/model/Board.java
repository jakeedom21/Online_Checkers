package com.webcheckers.model;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by qadirhaqq on 2/27/18.
 */
public class Board implements Serializable{
//    private int redPieces = 12;
//    private int whitePieces = 12;
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

    private void setBoardPieces(){
        EMPTY_ROWS.add(3);
        EMPTY_ROWS.add(4);
        // Populate the board from top to bottom
        for (int row = 0; row < MAX_DIM; row++) {
            if (!EMPTY_ROWS.contains(row)) { // not rows 3 or 4
                if (row % 2 == 0) {
                    for (int col = 1; col < MAX_DIM; col += 2) {
                        createPiece(row, col);
                    }
                }
                if (row % 2 == 1) {
                    for (int col = 0; col < MAX_DIM; col += 2) {
                        createPiece(row, col);
                    }
                }
            }
        }
    }

    private void createPiece(int row, int col) {
        String color = (row > MAX_DIM/2) ? PLAYER1_COLOR : PLAYER2_COLOR;
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

}

