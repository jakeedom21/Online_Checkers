package com.webcheckers.appl;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Session;

import static com.webcheckers.model.Message.MessageType.info;

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

        Board board = game.getBoard(currentPlayer);

        Space start = board.getSpace(jsonMove.getStart());
        Space end = board.getSpace(jsonMove.getEnd());
        Move move = new Move(start, end);

        boolean result = true; // MoveValidation.validMove(move.getStart(), move.getEnd(), game.getBoard());
        if (result) {
            game.queueMove(move);
        }

        return gson.toJson(result);
    }

    public Object submitMove(Request request, Response response) {
        Player currentPlayer = getPlayerFromRequest(request);
        Game game = currentPlayer.getGame();
        Move move = game.getNextMove();
        Space oldSpace =  move.getStart();
        Space newSpace = move.getEnd();
        game.movePiece(oldSpace, newSpace, currentPlayer);
        game.finishMove();
        return gson.toJson(new Message(MessageType.info, "Turn submitted successfully"));

    }

    public String backupMove(Request request, Response response) {
        Player currentPlayer = getPlayerFromRequest(request);
        Game game = currentPlayer.getGame();
        game.getNextMove();
        return gson.toJson(new Message(MessageType.info, "Success"));
    }

    public String checkTurn(Request request, Response response) {
        return gson.toJson(new Message(info,"true"));
    }
}

