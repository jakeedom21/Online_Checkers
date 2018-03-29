package com.webcheckers.utils;

public class Constants {
  public static final String PLAYER = "player";
  public static final String MESSAGE = "message";
  public static final String PLAYER_NAME = "playerName";
  public static final String TITLE = "title";
  public static final String NUM_USER = "numUsers";
  public static final String SIGNED_IN_PLAYER = "signedInPlayer";
  public static final String FREE_PLAYERS = "freePlayers";
  public static final String BUSY_OPPONENT_ERROR = "busyOpponentError";
  public static final String GAME_WON = "gameWon";
  public static final String OPPONENT_FORFEIT = "opponentForfeit";


  // URL Constants
  public static final String HOME_URL = "/";
  public static final String SIGN_IN = HOME_URL + "signin";
  public static final String SIGN_OUT = HOME_URL +  "signout";
  public static final String GAME_URL = HOME_URL + "game";
  public static final String VALIDATE_MOVE = HOME_URL + "validateMove";
  public static final String SUBMIT_TURN = HOME_URL + "submitTurn";
  public static final String BACKUP_MOVE = HOME_URL + "backupMove";
  public static final String CHECK_TURN = HOME_URL + "checkTurn";
  public static final String RESIGN_URL = HOME_URL + "resignGame";
  public static final String RESULT_URL = HOME_URL + "result";
  public static final String RULES_URL = HOME_URL + "rules";

  // GAME CONSTANTS
  public static final int MAX_DIM = 8;
}