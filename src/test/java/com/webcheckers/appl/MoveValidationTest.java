package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Board;
import com.webcheckers.model.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Created by Jake on 3/20/2018.
 * This is the test case for MoveValidation
 *
 * Note full implementation has not been completed
 */
@Tag("Application-tier")
public class MoveValidationTest {
    private static final Space FARLEFT = new Space(0,-1);
    private static final Space FARRIGHT = new Space(0, 9);
    private static final Space BELOW = new Space(-1, 0);
    private static final Space ABOVE = new Space(9, 0);
    private static final Space BADSPACE = new Space(0,0);
    private static final Space BADSPACE2 = new Space(1, 1);
    private static final Space NOSPACE = null;
    private static final Space GOODSPACE = new Space(0, 1);


    @Test
    public void testFarLeftSpace(){
        assertFalse(MoveValidation.validPlacement(FARLEFT));
    }

    @Test
    public void testFarRightSpace(){
        assertFalse(MoveValidation.validPlacement(FARRIGHT));
    }

    @Test
    public void testBelowSpace(){
        assertFalse(MoveValidation.validPlacement(BELOW));
    }

    @Test
    public void testAboveSpace(){
        assertFalse(MoveValidation.validPlacement(ABOVE));
    }

    @Test
    public void testBadSpace(){
        assertFalse(MoveValidation.validPlacement(BADSPACE));
    }

    @Test
    public void testBadSpace2(){
        assertFalse(MoveValidation.validPlacement(BADSPACE2));
    }

    @Test
    public void testNoSpace(){
        assertFalse(MoveValidation.validPlacement(NOSPACE));
    }

    @Test
    public void testGoodSpace(){
        assert(MoveValidation.validPlacement(GOODSPACE));
    }
}
