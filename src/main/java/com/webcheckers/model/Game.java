package com.webcheckers.model;

/**
 * Created by qadirhaqq on 2/28/18.
 */
public class Game {
    private final User p1;
    private final User p2;
    private Board board;
    private User playerTurn;
    private boolean forfeit = false;

    public Game(Integer id, User p1, User p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = new Board();
    }

    public User[] getPlayers() {
        User[] players = new User[2];
        players[0] = p1;
        players[1] = p2;
        return players;
    }

    public void setTurn() {
        if (this.playerTurn == p1) {
            playerTurn = p2;
        } else {
            playerTurn = p1;
        }
    }

}
