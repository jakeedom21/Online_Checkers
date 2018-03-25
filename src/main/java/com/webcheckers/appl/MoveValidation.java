package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 3/2/2018.
 *
 *
 */
public class MoveValidation {
    private final static int MAX_DIM = 8;

    /**
     *
     * Get the basic moves for all pieces
     * grabs all immediate possible moves
     * checks if the space is valid if not it is removed from the possibilities
     * after checking validity of basicMoves all those should be good possible moves
     * then checks the jumps to see if their is an enemy piece they are jumping
     * if there is not that jump is removed
     * returns the possible jumpMoves if array is not empty
     * basicMoves otherwise
     *
     *  @param piece
     * @return
     */
    public ArrayList<Space> basicMoves(Piece piece, Board gameBoard){
        ArrayList<Space> basicMoves = new ArrayList<>(4);
        ArrayList<Space> jumpMoves = new ArrayList<>(4);
        Space[][] board = gameBoard.getRaw();

        //gets all moves a piece should be able to make
        Space topRight = board[piece.getRowNumber() - 1][piece.getColNumber() + 1];
        basicMoves.add(topRight);
        Space topLeft = board[piece.getRowNumber() - 1] [piece.getColNumber() - 1];
        basicMoves.add(topLeft);
        Space jumpTopRight = board[topRight.getRow() - 1] [topRight.getCol() + 1];
        jumpMoves.add(jumpTopRight);
        Space jumpTopLeft = board[topRight.getRow() - 1] [topRight.getCol() - 1];
        jumpMoves.add(jumpTopLeft);

        //grabs moves a king should be able to make
        if(piece.isKing()){
            Space bottomRight = board[piece.getRowNumber() + 1] [piece.getColNumber() + 1];
            basicMoves.add(bottomRight);
            Space bottomLeft = board[piece.getRowNumber() + 1] [piece.getColNumber() - 1];
            basicMoves.add(bottomLeft);
            Space bottomRightJump = board[bottomRight.getRow() + 1] [bottomRight.getCol() + 1];
            jumpMoves.add(bottomRightJump);
            Space bottomLeftJump = board[bottomLeft.getRow() + 1][bottomLeft.getCol() - 1];
            jumpMoves.add(bottomLeftJump);
        }

        //removes all invalid basicMoves
        for(int i = 0; i < basicMoves.size(); i++){
            if(!basicMoves.get(i).isValid()){
                basicMoves.remove(i);
                i--;
            }
        }
        //nothing more needs to be done with basicMoves
        //cause validity is all they need

        //removes invalid jumpsMoves
        for(int j = 0; j < jumpMoves.size(); j++){
            if(!jumpMoves.get(j).isValid()){
                jumpMoves.remove(j);
                j--;
            }
        }
        //needs to check for a piece to jump and confirm its an opponents piece

        for(int jumpCheck = 0; jumpCheck < jumpMoves.size(); jumpCheck++){
            Space currentJump = jumpMoves.get(jumpCheck);
            int jumpRow = currentJump.getRow() + piece.getRowNumber();
            int jumpCol = currentJump.getCol() + piece.getColNumber();
            Space jumpOver = board[jumpRow][jumpCol];
            //if no piece in between jump and start remove jump
            if(jumpOver.getPiece() == null){
                jumpMoves.remove(jumpCheck);
                jumpCheck--;
            }
            else{
                //if piece is the same color as piece remove jump
                if(jumpOver.getPiece().getColor().equals(piece.getColor())){
                    jumpMoves.remove(jumpCheck);
                    jumpCheck--;
                }
            }
        }
        //Any jumpMoves past here are good jumps that can be made
        if(jumpMoves.size() > 0){
            return jumpMoves;
        }
        else{
            return basicMoves;
        }
    }

    /**
     * Checks to see if finalSpace is a valid place for a piece to be
     * @param finalSpace
     * @return true on empty space that a checkers piece should be able to access at some point,
     *         false on full spaces or any space a checkers piece shouldn't be able to access
     */
    public static boolean validPlacement(Space finalSpace) {
        if(finalSpace == null){
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
     * Will determine if its a validMove using the distance
     * Three types of moves, basic, jump, multijump
     * Basic:
     * Any move that does not involve jumping
     * Jump:
     * Any move that involves jumping over a piece
     * Multijump:
     * A subclass of sorts to jump will act as a system determining multijumps
     *
     * Only works one move at a time and does not validate multijumps
     *
     * @param start
     * @param end
     * @return true if its a valid move
     *         false if not a valid move
     */
    public boolean validMove(Space start, Space end, Board board) {
        int x_dist = Math.abs(start.getCol() - end.getCol());
        int y_dist = Math.abs(start.getRow() - end.getRow());
        if (x_dist < 1 || x_dist > 2) {
            return false;
        } else if (y_dist < 1 || y_dist > 2) {
            return false;
        }
        //gets the possible ending spots
        ArrayList<Space> possibleEnds = basicMoves(start.getPiece(), board);
        for (int i = 0; i < possibleEnds.size(); i++) {
            Space jump = possibleEnds.get(i);
            if (start.getCol() == jump.getCol() && start.getRow() == jump.getRow()) {
                return true;
            }
        }
        return false;
    }
}
