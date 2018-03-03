package com.webcheckers.model;

/**
 * Created by qadirhaqq on 2/28/18.
 */
public class Game {
    private final Player p1;
    private final Player p2;
    private Board board;
    private Player playerTurn;
    private boolean forfeit = false;
    private int id;

    public Game(Integer id, Player p1, Player p2) {
        this.id = id;
        this.p1 = p1;
        this.p2 = p2;
        this.board = new Board();
    }

    public Player[] getPlayers() {
        Player[] players = new Player[2];
        players[0] = p1;
        players[1] = p2;
        return players;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setTurn() {
        if (this.playerTurn == p1) {
            playerTurn = p2;
        } else {
            playerTurn = p1;
        }
    }

    public int getId(){
        return this.id;
    }
}

