package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
class PieceTest {

  @Test
  void setPosition() {
    // create piece
    Piece piece = new Piece(5, 10, "W");

    // set position and test
    piece.setPosition(10, 5);
    assertEquals(10, piece.getRowNumber());
    assertEquals(5, piece.getColNumber());
  }

  @Test
  void getRowNumber() {
    // create piece
    Piece piece = new Piece(5, 10, "W");

    // test row value
    assertEquals(5, piece.getRowNumber());
  }

  @Test
  void getColNumber() {
    // create piece
    Piece piece = new Piece(10, 5, "W");

    // test column value
    assertEquals(5, piece.getColNumber());
  }

  @Test
  void getColor() {
    // create piece
    Piece piece = new Piece(10, 10, "R");

    // test color value
    assertEquals("RED", piece.getColor());
  }

  @Test
  void setKing() {
    // create piece
    Piece piece = new Piece(10, 10, "W");
    piece.setKing();

    // test if king
    assertEquals(true, piece.isKing());
  }

  @Test
  void isKing() {
    // create piece
    Piece piece = new Piece(10, 10, "W");

    // test if king
    assertEquals(false, piece.isKing());
  }

  @Test
  void getType() {
    // create piece
    Piece piece = new Piece(10, 10, "W");

    // test type value
    assertEquals("SINGLE", piece.getType());
  }
}