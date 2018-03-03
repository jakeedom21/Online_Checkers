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

    /**
     * Start a new Game when a signed-in player click on an opponent
     * Called by handle method in GetGameRoute class
     * @param id for a game
     * @param player1 player who picked opponent, assign RED PieceColor
     * @param player2 player who got picked, assign WHITE PieceColor
     */
    public void startNewGame(int id, Player player1, Player player2){
        Game newGame = new Game(id, player1, player2);
        player1.assignGame(Player.PieceColor.RED, newGame);
        player2.assignGame(Player.PieceColor.WHITE, newGame);
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

