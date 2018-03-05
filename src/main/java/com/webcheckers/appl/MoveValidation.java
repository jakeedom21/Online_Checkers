package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;

import java.util.ArrayList;

/**
 * Created by Jake on 3/2/2018.
 * basicMoves:
 * Will get all possible single space moves from a piece does
 * only validation it does is that it doesn't go out of bounds
 *
 * Will probably use this to help pathfind to the final space
 *
 * For validation
 *
 * Current problems:
 * Finding the path in such a way it could be used for Player Help
 * Determining path when there are multiple valid ways to a space
 * Best way may be when it detects multiple valid paths to have the player do it one jump at a time
 *
 * Pathfinder
 */
public class MoveValidation {
    private final static int MAX_DIM = 8;

    /**
     * Get the basic moves for all pieces
     * this means the all moves from the single space/piece one space away
     * will see if it is a valid move later
     * @param piece
     * @return
     */
    public ArrayList<Space> basicMoves(Piece piece){
        ArrayList<Space> basicMoves = new ArrayList<>(4);
        Space topRight = new Space(piece.getRowNumber() - 1, piece.getColNumber() + 1);
        Space topLeft = new Space(piece.getRowNumber() - 1, piece.getColNumber() - 1);
        if(piece.getRowNumber() -1 < 0){
            //only get top right
            if(piece.getColNumber() - 1 < 0){
                basicMoves.add(topRight);
            }
            //only get top left
            else if(piece.getColNumber() + 1 >= MAX_DIM){
                basicMoves.add(topLeft);
            }
            //get both
            else{
                basicMoves.add(topLeft);
                basicMoves.add(topRight);
            }
        }
        //
        if(piece.isKing()) {
            Space bottomRight = new Space(piece.getRowNumber() + 1, piece.getColNumber() + 1);
            Space bottomLeft = new Space(piece.getRowNumber() + 1, piece.getColNumber() - 1);
            if (piece.getRowNumber() + 1 >= MAX_DIM) {
                //only gets the bottomRight
                if (piece.getColNumber() - 1 < 0) {
                    basicMoves.add(bottomRight);
                }
                //only get bottom left
                else if (piece.getColNumber() + 1 >= MAX_DIM) {
                    basicMoves.add(bottomLeft);
                }
                //get both
                else {
                    basicMoves.add(bottomLeft);
                    basicMoves.add(bottomRight);
                }
            }
        }
        return basicMoves;
    }

    // public ArrayList<Space> findPath(Piece movingPiece, )

    public boolean validMove(Piece movingPiece, Space finalSpace, Board gameBoard){
        //if both col and row are even or odd is an invalid move
        if(finalSpace.getCol()%2 == 0 && finalSpace.getRow()%2 == 0){
            return false;
        }
        else if(finalSpace.getCol()%2 == 1 && finalSpace.getRow()%2 == 1){
            return false;
        }
        //if finalSpace has a piece already in it
        else if(!finalSpace.getPiece().equals(null)){
            return false;
        }
        //not sure if actually needed as don't know if player actually can place outside board
        else if(finalSpace.getRow() < 0 || finalSpace.getCol() < 0){
            return false;
        }
        //for this in particular, if player can place outside board will need a get in board for MAX_DIM
        else if(finalSpace.getCol() >= MAX_DIM || finalSpace.getRow() >= MAX_DIM){
            return false;
        }
        //finalSpace is a valid place for a pieces to be
        //will need to no check if it can jump over pieces to finalSpace
        return false;
    }
}
