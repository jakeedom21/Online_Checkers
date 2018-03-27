package com.webcheckers.model;

/**
 * Created by qadirhaqq on 2/28/18.
 */
public class Game {


    private Board board;
    private String playerTurn;
    private Player winner;
    private boolean forfeit;
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
        this.board = new Board();
        this.playerTurn = p1.getPlayerName();
        this.forfeit = false;
        this.winner = null;
        p1.assignGame(Player.PieceColor.RED, this,p2);
        p2.assignGame(Player.PieceColor.WHITE, this,p1);
    }

    public void setOrientation(Player player){
        board.setBoardPieces(player);
    }

    public Player[] getPlayers() {
        Player[] players = new Player[2];
        players[0] = this.p1;
        players[1] = this.p2;
        return players;
    }

    public Board getBoard() {
        return this.board;
    }

    public String getPlayerTurn() {
        return this.playerTurn;
    }

    public void setTurn() {
        if (this.playerTurn.equals(p1.getPlayerName())) {
            playerTurn = p2.getPlayerName();
        } else {
            playerTurn = p1.getPlayerName();
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

    public boolean isWinner() {
        if (board.getP1Pieces() == 0) {
            this.winner.getPlayerName().equals(p2.getPlayerName());
            return true;
        } else if (board.getP2Pieces() == 0){
            this.winner.getPlayerName().equals(p1.getPlayerName());
            return true;
        } else
            return false;
    }

    /**
     * One of the player choose to resign the game. Player's opponent wins.
     * @param playername username of the player who chose to resign
      */
    public void setForfeit(String playername){
        String p1name = p1.getPlayerName();
        //String p2name = p2.getPlayerName();
        forfeit = true;

        if (playername.equals(p1name)) {
            winner = p2;
        } else {
            winner = p1;
        }
        p1.finishGame();
        p2.finishGame();
    }

    public boolean didPlayerResign() {
        return this.forfeit;
    }

    public int getId(){
        return this.id;
    }
}

