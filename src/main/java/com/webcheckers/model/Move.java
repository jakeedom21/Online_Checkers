package com.webcheckers.model;

public class Move {
    private static final int MAX_DIM = 8;
    private Space start;
    private Space end;

    public Move(Space start, Space end) {
        this.start = start;
        this.end = end;
    }

    public Space getStart() {
        return start;
    }

    public Space getEnd() {
        return end;
    }

    public String toString() {
        return "start: " + start.getRow() + "-" + start.getCol() + "\n end: " + end.getRow() + "- " + end.getCol();
    }

    public static boolean isValid(Space space) {
        //if both col and row are even or odd is an invalid move
        if(space.getCol()%2 == 0 && space.getRow()%2 == 0){
//            throw new IllegalArgumentException("Invalid Move: Row and Col are both even");
            return false;
        }
        else if(space.getCol()%2 == 1 && space.getRow()%2 == 1){
//            throw new IllegalArgumentException("Invalid Move: Row and Col are both odd");
            return false;
        }
        //if space has a piece already in it
        else if(space.hasPiece()){
//            throw new IllegalArgumentException("Invalid Move: Space already has piece");
            return false;
        }
        //not sure if actually needed as don't know if player actually can place outside board
        else if(space.getRow() < 0 || space.getCol() < 0){
//            throw new IllegalArgumentException("Invalid Move: Cannot place outside board");
            return false;
        }
        //for space in particular, if player can place outside board will need a get in board for MAX_DIM
        else if(space.getCol() >= MAX_DIM || space.getRow() >= MAX_DIM){
//            throw new IllegalArgumentException("Invalid Move: Cannot place outside board");
            return false;
        }
        else{
            return true;
        }
    }
}
