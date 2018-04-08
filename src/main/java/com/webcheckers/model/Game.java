package com.webcheckers.model;

import com.webcheckers.utils.Constants;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by qadirhaqq on 2/28/18.
 */
public class Game {


    private Board p1Board;
    private Board p2Board;
    private String playerTurn;
    private Player winner;
    private boolean forfeit;
    private Queue<Move> moveQueue;
    private int id;
    private Player p1;
    private Player p2;

    /**
     * Start a new Game when a signed-in player click on an opponent
     * Called by GameController when request receive
     * @param id for a game
     * @param p1 player who picked opponent, assign RED PieceColor
     * @param p2 player who got picked, assign WHITE PieceColor
     */
    public Game(Integer id, Player p1, Player p2) {
        this.id = id;
        this.p1 = p1;
        this.p2 = p2;
        this.p1Board = new Board();
        this.p2Board = new Board();
        this.p2Board.flip();
        this.playerTurn = p1.getPlayerName();
        this.forfeit = false;
        this.winner = null;
        this.moveQueue = new LinkedList<>();
        p1.assignGame(Constants.PieceColor.RED, this,p2);
        p2.assignGame(Constants.PieceColor.WHITE, this,p1);
    }

    /**
     * get the first player
     * @return first player
     */
    public Player getPlayer1() {
        return p1;
    }

    /**
     * get the second player
     * @return second player
     */
    public Player getPlayer2 () {
        return p2;
    }

    public Board getBoard(Player p) {
        return p.equals(p1) ? p1Board : p2Board;
    }

    public String getPlayerTurn() {
        return this.playerTurn;
    }

    public void finishMove() {
        playerTurn =  playerTurn.equals(p1.getPlayerName()) ? p2.getPlayerName() : p1.getPlayerName();
    }

    /**
     * See if the game is finished
     * @return true if there's a winner of game, false otherwise
     */
    public boolean isGameWon(){
        return winner != null;
    }

    /**
     * pre: game is finished (there is a winner)
     * @return String username of the winning player
     */
    public String getWinner() {
        return this.winner.getPlayerName();
    }

    public boolean isWinner() {
        if (p1Board.getP1Pieces() == 0 && p2Board.getP1Pieces() == 0) {
            this.winner = p2;
            return true;
        } else if (p1Board.getP2Pieces() == 0 && p2Board.getP2Pieces() == 0){
            this.winner = p1;
            return true;
        } else
            return false;
    }

    /**
     * One of the player choose to resign the game. Player's opponent wins.
     * Does not set Player objects' fields to null
     * Player.finishGame() called separately
     * @param playername username of the player who chose to resign
      */
    public void setForfeit(String playername){
        String p1name = p1.getPlayerName();
        forfeit = true;

        if (playername.equals(p1name)) {
            winner = p2;
        } else {
            winner = p1;
        }
    }

    /**
     * See if either player has resigned from this game
     * @return bool true if either forfeit
     */
    public boolean didPlayerResign() {
        return this.forfeit;
    }

    /**
     * gets the current id of the game
     * @return int
     */
    public int getId(){
        return this.id;
    }

    /**
     * Adds a move to the game queue
     * @param move - move to be added
     */
    public void queueMove(Move move){
        this.moveQueue.add(move);
    }

    /**
     * Gets and removes next move from the game queue
     * @return move object
     */
    public Move getNextMove() {
        return this.moveQueue.poll();
    }

    /**
     * Moves a piece and determines if a jump has been made
     * @param start - start space
     * @param end - end space
     * @param p - player making the move
     */
    public void movePiece(Space start, Space end, Player p) {
        int dist = Math.abs(start.getRow() - end.getRow());
        int mid_row = (int)Math.floor((start.getRow() + end.getRow())/2);
        int mid_col = (int)Math.floor((start.getCol() + end.getCol())/2);
        Space mid_point = new Space(mid_row, mid_col);
        Board b = p.equals(p1) ? p1Board : p2Board;
        b.movePiece(start, end);
        System.out.println("End: " + end.getRow() + "-" + end.getCol());
        if (dist >= 2)
            b.removePiece(mid_point);
        Board newOpponentBoard = new Board(b);
        newOpponentBoard.flip();
        if (p.equals(p1))
            p2Board = newOpponentBoard;
        else
            p1Board = newOpponentBoard;
        System.gc();
//        if (currentPlayer.equals(this.p1)) {
//            p1Board.movePiece(start, end);
//            //means move is a jump
//            if(dist >= 2){
//                p1Board.removePiece(mid_point);
//            }
//            Board newP2board = new Board(p1Board);
//            newP2board.flip();
//            p2Board = newP2board;
//        } else {
//            p2Board.movePiece(start, end);
//            //means move is a jump
//            if(dist >= 2){
//                p2Board.removePiece(mid_point);
//            }
//            Board newP1board = new Board(p2Board);
//            newP1board.flip();
//            p1Board = newP1board;
//
//        }
    }
}

