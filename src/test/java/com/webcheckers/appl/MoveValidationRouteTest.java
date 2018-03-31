package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jake on 3/20/2018.
 * This is the test case for MoveValidation
 *
 * Note full implementation has not been completed
 */
@Tag("Application-tier")
public class MoveValidationRouteTest {
    private Space bad;
    private Space bad2;
    private Space none;
    private Piece red;
    private Piece red2;
    private Piece white;
    private Piece white2;
    private Board board;


    @BeforeEach
    public void setUp(){
        bad = new Space(0,0);
        bad2 = new Space(1, 1);
        none = null;
        board = new Board();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board.getSpace(i,j).setPiece(null);
            }
        }
        red = new Piece(0,0,"R");
        red2 = new Piece(0,0,"R");
        white = new Piece(0,0,"W");
        white2 = new Piece(0,0,"W");
    }

    /**
     * Takes a look at each board config to confirm placement

    public void confirmBoard(){
        System.out.println(board.toString());
    }
     */


    @Test
    public void testBadSpace(){
        assertSame("Piece's can't go into this space", MoveValidation.validPlacement(bad, board));
    }


    @Test
    public void testBadSpace2(){
        assertSame("Piece's can't go into this space", MoveValidation.validPlacement(bad2, board));
    }

    @Test
    public void testNoSpace(){
        assertSame("Space does not exist", MoveValidation.validPlacement(none, board));
    }


    @Test
    public void testValidBasiccMove(){
        red.setPosition(2,3);
        Space start = board.getSpace(2, 3);
        start.setPiece(red);
        Space end = board.getSpace(1, 4);
        assertSame("", MoveValidation.validMove(start, end, board));
    }

    @Test
    public void testInvalidBasicMove(){
        red.setPosition(2, 3);
        Space start = board.getSpace(2,3);
        start.setPiece(red);
        red2.setPosition(1,4);
        Space end = board.getSpace(1,4);
        end.setPiece(red2);
        assertSame("There is already a piece at the end", MoveValidation.validMove(start, end, board));
    }

    @Test
    public void testValidJumpMove(){
        red.setPosition(2, 3);
        Space start = board.getSpace(2,3);
        start.setPiece(red);
        white.setPosition(1,4);
        Space mid = board.getSpace(1,4);
        mid.setPiece(white);
        Space end = board.getSpace(0,5);
        assertSame("", MoveValidation.validMove(start, end, board));
    }

    @Test
    public void testInvalidJumpMove(){
        red.setPosition(2, 3);
        Space start = board.getSpace(2,3);
        start.setPiece(red);
        white.setPosition(1,4);
        Space mid = board.getSpace(1,4);
        mid.setPiece(white);
        white2.setPosition(0,5);
        Space end = board.getSpace(0,5);
        end.setPiece(white2);
        System.out.println("Invalid jump move" + end.hasPiece());
        assertSame("There is already a piece at the end", MoveValidation.validMove(start, end, board));
    }

    @Test
    public void testValidCorner(){
        red.setPosition(0, 1);
        Space start = board.getSpace(0,1);
        red.setKing();
        start.setPiece(red);
        white.setPosition(1,2);
        Space mid = board.getSpace(1,2);
        mid.setPiece(white);
        Space end = board.getSpace(2,3);
        assertSame("", MoveValidation.validMove(start, end, board));
    }

    @Test
    public void testInvalidCorner(){
        red.setPosition(0, 1);
        Space start = board.getSpace(0,1);
        start.setPiece(red);
        white.setPosition(1,2);
        Space mid = board.getSpace(1,2);
        mid.setPiece(white);
        white2.setPosition(2,3);
        Space end = board.getSpace(2,3);
        end.setPiece(white2);
        assertSame("There is already a piece at the end", MoveValidation.validMove(start, end, board));
    }

    @Test
    public void testValidBasicKing(){
        red.setPosition(2,3);
        red.setKing();
        Space start = board.getSpace(2, 3);
        start.setPiece(red);
        Space end = board.getSpace(3, 4);
        assertSame("", MoveValidation.validMove(start, end, board));
    }

    @Test
    public void testValidJumpKing(){
        red.setPosition(2, 3);
        red.setKing();
        Space start = board.getSpace(2,3);
        start.setPiece(red);
        white.setPosition(3,4);
        Space mid = board.getSpace(3,4);
        mid.setPiece(white);
        Space end = board.getSpace(4, 5);
        assertSame("", MoveValidation.validMove(start, end, board));
    }

    @Test
    public void testInvalidBasicKing(){
        red.setPosition(2,3);
        red.setKing();
        Space start = board.getSpace(2, 3);
        start.setPiece(red);
        white.setPosition(3,4);
        Space end = board.getSpace(3, 4);
        end.setPiece(white);
        assertSame("There is already a piece at the end", MoveValidation.validMove(start, end, board));
    }

    @Test
    public void testInvalidJumpKing(){
        red.setPosition(2, 3);
        Space start = board.getSpace(2,3);
        start.setPiece(red);
        white.setPosition(3,4);
        Space mid = board.getSpace(3, 4);
        mid.setPiece(white);
        white2.setPosition(4,5);
        Space end = board.getSpace(4, 5);
        end.setPiece(white2);
        assertSame("There is already a piece at the end", MoveValidation.validMove(start, end, board));
    }
}
