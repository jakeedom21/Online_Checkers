package com.webcheckers.appl;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Session;

public class MoveManager {

    PlayerLobby playerLobby;
    Gson gson;

    private Game getGameFromRequest(Request request){
        Session currentSession = request.session();
        String playerName = currentSession.attribute("playerName");
        Player currentPlayer = playerLobby.getPlayerByUsername(playerName);
        return currentPlayer.getGame();
    }

    public MoveManager(PlayerLobby playerLobby, Gson gson) {
        this.playerLobby = playerLobby;
        this.gson = gson;
    }

    public String validateMove(Request request, Response response) {
        Game game = getGameFromRequest(request);
        Move jsonMove = gson.fromJson(request.body(), Move.class);
        Board board = game.getBoard();

        Space start = board.getSpace(jsonMove.getStart());
        Space end = board.getSpace(jsonMove.getEnd());
        Move move = new Move(start, end);

        boolean result = true; // MoveValidation.validMove(move.getStart(), move.getEnd(), game.getBoard());
        if (result) {
            game.queueMove(move);
        }

        String resultJson = gson.toJson(result);
        return resultJson;
    }

    public Object submitMove(Request request, Response response) {
        Game game = getGameFromRequest(request);
        Move move = game.getNextMove();
        Space oldSpace =  move.getStart();
        Space newSpace = move.getEnd();
        Board board = game.getBoard();
        board.movePiece(oldSpace, newSpace);
        response.redirect("/game");
        game.finishMove();
        System.out.println(gson.toJson(new Message(MessageType.info, "Turn submitted successfully")));
        return  gson.toJson(new Message(MessageType.info, "Turn submitted successfully"));
    }

    public String backupMove(Request request, Response response) {
        return "SOMETHING";
    }

    public String checkTurn(Request request, Response response) {
        return gson.toJson(new Message(MessageType.info,"true"));
    }

}

