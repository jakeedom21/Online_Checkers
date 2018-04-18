package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;
import com.webcheckers.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Jake on 3/2/2018.
 *
 *
 */
public class MoveValidation {

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
    public static ArrayList<Space> basicMoves(Piece piece, Board gameBoard){
        ArrayList<Space> basicMoves = new ArrayList<>(4);
        ArrayList<Space> jumpMoves = new ArrayList<>(4);

        for(int i = -1; i <= 1; i += 2){
            Space basicMove = new Space(piece.getRowNumber() - 1, piece.getColNumber() + i);
            basicMoves.add(basicMove);
            Space jumpMove = new Space(basicMove.getRow() - 1, basicMove.getCol() + i);
            jumpMoves.add(jumpMove);
        }

        //grabs moves a king should be able to make
        if(piece.isKing()){

            for(int i = -1; i <= 1; i += 2){
                Space basicMove = new Space(piece.getRowNumber() + 1, piece.getColNumber() + i);
                basicMoves.add(basicMove);
                Space jumpMove = new Space(basicMove.getRow() + 1, basicMove.getCol() + i);
                jumpMoves.add(jumpMove);
            }
        }

        //removes all invalid basicMoves
        for(int i = 0; i < basicMoves.size(); i++){
            Space basicCheck = basicMoves.get(i);
            if(!Move.isValid(basicCheck)){
                basicMoves.remove(i);
                i--;
            }
            else{
                basicCheck = gameBoard.getSpace(basicCheck.getRow(), basicCheck.getCol());
                basicMoves.set(i, basicCheck);
                if(basicCheck.hasPiece()){
                    basicMoves.remove(i);
                    i--;
                }
            }
        }
        //nothing more needs to be done with basicMoves
        //cause validity is all they need

        //removes invalid jumpsMoves
        for(int j = 0; j < jumpMoves.size(); j++){
            Space basicJump = jumpMoves.get(j);
            if(!Move.isValid(basicJump)){
                jumpMoves.remove(j);
                j--;
            }
            else{
                basicJump = gameBoard.getSpace(basicJump.getRow(), basicJump.getCol());
                jumpMoves.set(j, basicJump);
                if(basicJump.hasPiece()){
                    jumpMoves.remove(j);
                    j--;
                }

            }
        }
        //needs to check for a piece to jump and confirm its an opponents piece
        //the mid space is implicitly valid in terms of space placement
        for(int jumpCheck = 0; jumpCheck < jumpMoves.size(); jumpCheck++){
            Space currentJump = jumpMoves.get(jumpCheck);
            int jumpRow = (int) Math.floor((currentJump.getRow() + piece.getRowNumber()) / 2);
            int jumpCol = (int) Math.floor((currentJump.getCol() + piece.getColNumber()) / 2);
            Space jumpOver = gameBoard.getSpace(jumpRow, jumpCol);
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

    public static ArrayList<Space> getMustJump(String color, Board board){
        ArrayList<Space> mustJump = new ArrayList<>();
        for(int r = 0; r < Constants.MAX_DIM; r++){
            boolean fixed_even = false;
            for(int c = 0; c < Constants.MAX_DIM; c += 2) {
                if (r % 2 == 0 && !fixed_even) {
                    c = 1;
                    fixed_even = true;
                }
                //gets piece of same color needed
                Space curr_space = board.getSpace(r, c);
                if (curr_space.hasPiece()) {
                    Piece curr_piece = curr_space.getPiece();
                    if (curr_piece.getColor().equals(color)) {
                        ArrayList<Space> curr_piece_moves = basicMoves(curr_piece, board);
                        //has moves
                        if (curr_piece_moves.size() != 0) {
                            Space end_jump = curr_piece_moves.get(0);
                            //has at least one jump
                            if (Math.abs(curr_space.getCol() - end_jump.getCol()) == 2) {
                                mustJump.add(curr_space);
                            }
                        }
                    }
                }
            }
        }
        return mustJump;
    }

    /**
     * Checks to see if finalSpace is a valid place for a piece to be
     * @param finalSpace
     * @return true on empty space that a checkers piece should be able to access at some point,
     *         false on full spaces or any space a checkers piece shouldn't be able to access
     */
    public static String validPlacement(Space finalSpace, Board gameBoard){
        if(finalSpace == null){
            return "Space does not exist";
        }
        //if both col and row are even or odd is an invalid move
        if (finalSpace.getCol() % 2 == 0 && finalSpace.getRow() % 2 == 0) {
            return "Piece's can't go into this space";
        } else if (finalSpace.getCol() % 2 == 1 && finalSpace.getRow() % 2 == 1) {
            return "Piece's can't go into this space";
        }
        //not sure if actually needed as don't know if player actually can place outside board
        else if (finalSpace.getRow() < 0 || finalSpace.getCol() < 0) {
            return "Space does not exist";
        }
        //for this in particular, if player can place outside board will need a get in board for Constants.MAX_DIM
        else if (finalSpace.getCol() >= Constants.MAX_DIM || finalSpace.getRow() >= Constants.MAX_DIM) {
            return "Space does not exist";
        }
        //if finalSpace has a piece already in it
        finalSpace = gameBoard.getSpace(finalSpace.getRow(), finalSpace.getCol());
        if(finalSpace.hasPiece()) {
            return "There is already a piece there";
        }
        else {
            return "";
        }
    }

    public static HashMap<Space, Space> moveJumpList(String playerColor, Board board) {
        HashMap<Space, Space> spaceMovesList = new HashMap<>();
        ArrayList<Space> jumpSpaces = MoveValidation.getMustJump(playerColor, board);
        if (jumpSpaces.isEmpty()) {
            for (int r = 0; r < Constants.MAX_DIM; r++) {
                boolean adjusted = false;
                for (int c = 0; c < Constants.MAX_DIM; c += 2) {
                    if (r % 2 == 0 && !adjusted) {
                        c = 1;
                        adjusted = true;
                    }
                    Space checking = board.getSpace(r, c);
                    if (checking.hasPiece()) {
                        if (checking.getPiece().getColor().equals(playerColor)) {
                            ArrayList<Space> moveList = MoveValidation.basicMoves(checking.getPiece(), board);
                            //moveList is not empty
                            if (!moveList.isEmpty()) {
                                spaceMovesList.put(checking, moveList.get(0));
                            }
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < jumpSpaces.size(); i++) {
                ArrayList<Space> moveList = MoveValidation.basicMoves(jumpSpaces.get(i).getPiece(), board);
                //has a possible move
                if (!moveList.isEmpty()) {
                    spaceMovesList.put(jumpSpaces.get(i), moveList.get(0));
                }
            }
        }
        return spaceMovesList;
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
     * @return the reason for the invalid move if invalid
     *         "Too large of a move": move/jump that's too big
     *         "Too small of a move": move/jump that's too small
     *         "Opponent's piece protected by another opponent's piece"
     *         return "" if valid move
     */
    public static String validMove(Space start, Space end, Board board) {
        String validPlacement = validPlacement(end, board);
        //if the end space is not valid it will print an error
        if(!validPlacement.equals("")) {
            System.out.println(validPlacement);
        }
        //ensures that end is at least within the board
        //gets the possible ending spots

        ArrayList<Space> possibleEnds = basicMoves(start.getPiece(), board);
        for (int i = 0; i < possibleEnds.size(); i++) {
            Space jump = possibleEnds.get(i);
            if (end.getCol() == jump.getCol() && end.getRow() == jump.getRow()) {
                return "";
            }
        }
        if(end.hasPiece()){
            return "There is already a piece at the end";
        }
        //This looks for errors in the jump
        int row_mid = (int)Math.floor((start.getRow() + end.getRow())/2);
        int col_mid = (int)Math.floor((start.getCol() + end.getCol())/2);
        Space mid_space = board.getSpace(row_mid, col_mid);
        String start_color = start.getPiece().getColor();
        if(mid_space.hasPiece()){
            if (mid_space.getPiece().getColor().equals(start_color)){
                return "Can't jump over your own piece";
            }
        }
        return "Invalid move";
        //other errors are for basic moves
    }
}
