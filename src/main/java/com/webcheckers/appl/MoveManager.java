package com.webcheckers.appl;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Session;

import static com.webcheckers.model.Message.MessageType.info;
import static com.webcheckers.model.Message.MessageType.error;

public class MoveManager {

    private PlayerLobby playerLobby;
    private Gson gson;

    private Player getPlayerFromRequest(Request request){
        Session currentSession = request.session();
        String playerName = currentSession.attribute("playerName");
        return playerLobby.getPlayerByUsername(playerName);
    }

    public MoveManager(PlayerLobby playerLobby, Gson gson) {
        this.playerLobby = playerLobby;
        this.gson = gson;
    }

    public String validateMove(Request request, Response response) {
        Player currentPlayer = getPlayerFromRequest(request);
        Game game = currentPlayer.getGame();
        Move jsonMove = gson.fromJson(request.body(), Move.class);

        Message message;
        String result = MoveValidation.validMove(jsonMove.getStart(), jsonMove.getEnd(), game.getBoard(currentPlayer));
        //valid move checks if jump or not
        if (result == "") {
            message = new Message(info, "Valid Move");
            game.queueMove(jsonMove);
            int col_dist = (int)Math.floor(jsonMove.getStart().getCol() - jsonMove.getEnd().getCol());
            System.out.println("col_dist is " + col_dist);
            //is a jump and must remove jumped piece
            if(col_dist >= 2){
                int mid_col = (int)Math.floor((jsonMove.getStart().getCol() + jsonMove.getEnd().getCol())/2);
                int mid_row = (int)Math.floor((jsonMove.getStart().getRow() + jsonMove.getEnd().getRow())/2);
                game.getBoard(currentPlayer).getSpace(mid_row, mid_col).setPiece(null);
            }
        }
        //invalid move
        else {
            message = new Message(error, result);
        }

        return gson.toJson(message);
    }

    public Object submitMove(Request request, Response response) {
        Player currentPlayer = getPlayerFromRequest(request);
        Game game = currentPlayer.getGame();
        Move move = game.getNextMove();
        Space oldSpace =  move.getStart();
        Space newSpace = move.getEnd();
        game.movePiece(oldSpace, newSpace, currentPlayer);
        game.finishMove();
        return gson.toJson(new Message(info, "Turn submitted successfully"));

    }

    public String backupMove(Request request, Response response) {
        Player currentPlayer = getPlayerFromRequest(request);
        Game game = currentPlayer.getGame();
        game.getNextMove();
        return gson.toJson(new Message(info, "Success"));
    }

    public String checkTurn(Request request, Response response) {
        return gson.toJson(new Message(info,"true"));
    }
}

