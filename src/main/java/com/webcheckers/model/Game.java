package com.webcheckers.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by qadirhaqq on 2/28/18.
 */
public class Game {


    private Board p1Board;
    private Board p2Board;
    private String playerTurn;
    private String winner;
    private boolean forfeit = false;
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
        this.moveQueue = new LinkedList<>();
        p1.assignGame(Player.PieceColor.RED, this,p2);
        p2.assignGame(Player.PieceColor.WHITE, this,p1);
    }


    public Player[] getPlayers() {
        Player[] players = new Player[2];
        players[0] = this.p1;
        players[1] = this.p2;
        return players;
    }

    public Board getBoard(Player p) {
        if(p.equals(p1)){
            return this.p1Board;
        }
        return this.p2Board;
    }

    public String getPlayerTurn() {
        return this.playerTurn;
    }

    public void finishMove() {
        if (this.playerTurn.equals(p1.getPlayerName())) {
            playerTurn = p2.getPlayerName();
        } else {
            playerTurn = p1.getPlayerName();
        }
    }

    public String getWinner() {
        return this.winner;
    }

    public boolean isWinner() {
        if (p1Board.getP1Pieces() == 0 && p2Board.getP1Pieces() == 0) {
            this.winner = p2.getPlayerName();
            return true;
        } else if (p1Board.getP2Pieces() == 0 && p2Board.getP2Pieces() == 0){
            this.winner = p1.getPlayerName();
            return true;
        } else
            return false;
    }

    public boolean didPlayerResign() {

        return this.forfeit;
    }

    public int getId(){
        return this.id;
    }

    public void queueMove(Move move){
        this.moveQueue.add(move);
    }

    public Move getNextMove() {
        return this.moveQueue.poll();
    }

    public void movePiece(Space start, Space end, Player currentPlayer) {
        if (currentPlayer.equals(this.p1)) {
            p1Board.movePiece(start, end);
            Board newP2board = new Board(p1Board);
            newP2board.flip();
            p2Board = newP2board;
        } else {
            p2Board.movePiece(start, end);
            Board newP1board = new Board(p2Board);
            newP1board.flip();
            p1Board = newP1board;

        }




    }
}

