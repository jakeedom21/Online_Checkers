package com.webcheckers.model;

import com.webcheckers.appl.MoveValidation;
import com.webcheckers.utils.Constants;

import java.util.ArrayList;
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
    private ArrayList<Move> replayQueue;
    private Queue<Move> copyQueue;
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
        this.replayQueue = new ArrayList<>();
        p1.assignGame(Constants.PieceColor.RED, this, p2);
        p2.assignGame(Constants.PieceColor.WHITE, this, p1);
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
        if ( p1Board.getP1Pieces() == 0 && p2Board.getP1Pieces() == 0) {
            this.winner = p2;
        } else if (p1Board.getP2Pieces() == 0 && p2Board.getP2Pieces()==0 ){
            this.winner = p1;
        }
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

    /**
     * One of the player choose to resign the game. Player's opponent wins.
     * Does not set Player objects' fields to null
     * Player.finishGame() called separately
     * @param playername username of the player who chose to resign
      */
    public void setForfeit(String playername){
        String p1name = p1.getPlayerName();
        this.forfeit = true;
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

    public void commitMove(Move m) {
        this.movePiece(m);
        this.replayQueue.add(m);
    }

    /**
     * Moves a piece and determines if a jump has been made
     * @param m the move itself
     */
    public void movePiece(Move m) {
        Space start = m.getStart();
        Space end = m.getEnd();
        Player p = m.getPlayer();
        Board b = p.equals(p1) ? p1Board : p2Board;
        b.movePiece(start, end);
        int dist = Math.abs(start.getRow() - end.getRow());
        int mid_row = (int)Math.floor((start.getRow() + end.getRow())/2);
        int mid_col = (int)Math.floor((start.getCol() + end.getCol())/2);
        Space mid_point = new Space(mid_row, mid_col);
        if (dist >= 2)
            b.removePiece(mid_point);
        Board newOpponentBoard = new Board(b);
        newOpponentBoard.flip();
        if (p.equals(p1))
            p2Board = newOpponentBoard;
        else
            p1Board = newOpponentBoard;
    }

    /**
     * Copy references from the moves in the replayQueue to those into the copyQueue
     */
    public void copyReplayIntoQueue(){
        copyQueue = new LinkedList<>();
        copyQueue.add(null);
        copyQueue.addAll(replayQueue);
        copyQueue.add(null);
    }

    /**
     * Reset the board, primarily used for replay functionality
     */
    public void resetBoard() {
        this.p1Board = new Board();
        this.p2Board = new Board();
        this.p2Board.flip();
    }

    /**
     * Replays the game move by move from the copyQueue
     */
    public void replayGame() {
        Move m = copyQueue.poll();
        if(m != null)
            this.movePiece(m);
    }

    /**
     * Returns the size of the copyQueue
     * @return int size of the copyQueue
     */
    public int getCopyQueueSize() {
        return this.copyQueue.size();
    }


}

