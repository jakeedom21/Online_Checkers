package com.webcheckers.appl;

import com.webcheckers.model.Space;
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
    private Space above;
    private Space good;


    @BeforeEach
    public void setUp(){
        bad = new Space(0,0);
        bad2 = new Space(1, 1);
        none = null;
    }

    @Test
    public void testBadSpace(){
        assertFalse(MoveValidation.validPlacement(bad));
    }


    @Test
    public void testBadSpace2(){
        assertFalse(MoveValidation.validPlacement(bad2));
    }

    @Test
    public void testNoSpace(){
        assertFalse(MoveValidation.validPlacement(none));
    }
}
