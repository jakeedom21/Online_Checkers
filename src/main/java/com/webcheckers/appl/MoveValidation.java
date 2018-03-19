package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 3/2/2018.
 * basicMoves:
 * Will get all possible single space moves from a piece does
 * only validation it does is that it doesn't go out of bounds
 *
 *
 */
public class MoveValidation {
    private final static int MAX_DIM = 8;

    /**
     *
     * WILL BE NEEDED LATER ON
     *
     * Get the basic moves for all pieces
     * this means the all moves from the single space/piece one space away
     * will see if it is a valid move later
     * @param piece
     * @return
     */
    public ArrayList<Space> basicMoves(Piece piece, Board gameBoard){
        ArrayList<Space> basicMoves = new ArrayList<>(4);
        ArrayList<Space> jumpMoves = new ArrayList<>(4);
        Space topRight = new Space(piece.getRowNumber() - 1, piece.getColNumber() + 1);
        Space topLeft = new Space(piece.getRowNumber() - 1, piece.getColNumber() - 1);
        Space jumpTopRight = new Space(topRight.getRow() - 1, topRight.getCol() + 1);
        Space jumpTopLeft = new Space(topRight.getRow() - 1, topRight.getCol() - 1);
        Space[][] board = gameBoard.getRaw();
        boolean jumpPresent = false;
        if(piece.getRowNumber() -1 < 0){
            //only get top right
            if(piece.getColNumber() + 1 > MAX_DIM){
                if(board[topRight.getRow()][topRight.getCol()].getPiece() == null){
                    basicMoves.add(topRight);
                }
                //jumping over opponents piece
                else if(!board[topRight.getRow()][topRight.getCol()].getPiece().getColor().equals(piece.getColor())){
                    //valid jump
                    if(jumpTopRight.getRow() >= 0 && jumpTopRight.getCol() < MAX_DIM){
                        if(board[jumpTopRight.getRow()][jumpTopRight.getCol()].getPiece() == null){
                            jumpPresent = true;
                            jumpMoves.add(jumpTopRight);
                        }
                    }
                }
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
            Space bottomRightJump = new Space(bottomRight.getRow() + 1, bottomRight.getCol() + 1);
            Space bottomLeftJump = new Space(bottomLeft.getRow() + 1, bottomLeft.getCol() - 1);
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
        if(jumpPresent){
            return jumpMoves;
        }
        else{
            return basicMoves;
        }
    }

    /**
     * Checks to see if finalSpace is a valid place for a piece to be
     * @param finalSpace
     * @param gameBoard
     * @return true on empty space that a checkers piece should be able to access at some point,
     *         false on full spaces or any space a checkers piece shouldn't be able to access
     */
    public boolean validPlacement(Space finalSpace, Board gameBoard) {
        //validates the gameBoard
        if (gameBoard == null) {
            return false;
        }
        //if both col and row are even or odd is an invalid move
        if (finalSpace.getCol() % 2 == 0 && finalSpace.getRow() % 2 == 0) {
            return false;
        } else if (finalSpace.getCol() % 2 == 1 && finalSpace.getRow() % 2 == 1) {
            return false;
        }
        //if finalSpace has a piece already in it
        else if (!finalSpace.getPiece().equals(null)) {
            return false;
        }
        //not sure if actually needed as don't know if player actually can place outside board
        else if (finalSpace.getRow() < 0 || finalSpace.getCol() < 0) {
            return false;
        }
        //for this in particular, if player can place outside board will need a get in board for MAX_DIM
        else if (finalSpace.getCol() >= MAX_DIM || finalSpace.getRow() >= MAX_DIM) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Will determine if its a validMove us'in some good old math
     * Three types of moves, basic, jump, multijump
     * Basic:
     * Any move that does not involve jumping
     * Jump:
     * Any move that involves jumping over a piece
     * Multijump:
     * A subclass of sorts to jump will act as a system determining multijumps
     *
     * Will need to determine if the player can do a move as if they can
     * it will invalidate if it were a valid basic move
     * Will do so by grabbing all basic moves the player can make and
     * check the spots it can go and see if there is an opponent's piece
     * Additionally check if the player can jump that piece
     * It could add it too a list and then if the list has anything look at that
     *
     * First determines the distance of the move since it will be each move will be
     * either a jump or a move which means a difference in x/y by 2/2 and 1/1 for each
     * anything with a difference larger then this is invalid
     *
     *
     * @param start
     * @param end
     * @return true if its a valid move
     *         false if not a valid move
     */
    public boolean validMove(Space start, Space end) {
        int x_dist = Math.abs(start.getCol() - end.getCol());
        int y_dist = Math.abs(start.getRow() - end.getRow());
        if (x_dist < 1 || x_dist > 2) {
            return false;
        }
        else if(y_dist < 1 || y_dist > 2){
            return false;
        }


        //otherwise the distance is a single move/jump
        //Single move onto empty diagonal space
        if(x_dist == 1 && y_dist == 1){
            return basicMove(start, end);
        }
        return false;
    }

    /**
     * Takes in the moves with the assumption it is confirming a valid simple move
     * Must take into account
     * Color of piece
     * If its a king or not
     *
     * Non king red pieces
     * @param start
     * @param end
     * @return true if its a valid basic move
     *         false if otherwise
     */
    public boolean basicMove(Space start, Space end){
        Piece movingPiece = start.getPiece();
        return false;
    }

    public boolean validJump(Space start, Space end){
        return false;
    }

    /**
     * Will find all available jumps for a piece
     * if the list is empty, then no viable jumps
     * With multiple jumps then any in the list are viable
     *
     * Should be a max of 4 jumps at any one time
     * Due to how the board is set up every player will have their pieces at row 7
     * @param start
     * @return
     */
    public List<Space> findJumps(Space start){
        return null;
    }

}
