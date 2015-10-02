package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.Arrays;

public class SingleJumpTest {

    @Test
    public void testSingleJump() {
        int expectedStartPosition = 10;
        int expectedEndingPosition = 19;
        Board expectedBoard = new Board();
        SingleJump jump = new SingleJump(expectedStartPosition, expectedEndingPosition,
                expectedBoard);

        assertEquals(expectedStartPosition, jump.getStartingPosition());
        assertEquals(expectedEndingPosition, jump.getEndingPosition());
        assertEquals(expectedBoard, jump.getBoard());
        assertEquals(expectedBoard.getPiece(expectedStartPosition), jump.getPiece());

        int expectedJumpedPosition = 15;
        int actualJumpedPosition = jump.getJumpedPositions().get(0);
        assertEquals(expectedJumpedPosition, actualJumpedPosition);

        expectedEndingPosition = 17;
        jump = new SingleJump(expectedStartPosition, expectedEndingPosition, expectedBoard);
        expectedJumpedPosition = 14;
        actualJumpedPosition = jump.getJumpedPositions().get(0);
        assertEquals(expectedJumpedPosition, actualJumpedPosition);

        expectedStartPosition = 23;
        expectedEndingPosition = 14;
        jump = new SingleJump(expectedStartPosition, expectedEndingPosition, expectedBoard);
        expectedJumpedPosition = 18;
        actualJumpedPosition = jump.getJumpedPositions().get(0);
        assertEquals(expectedJumpedPosition, actualJumpedPosition);

        expectedStartPosition = 23;
        expectedEndingPosition = 16;
        jump = new SingleJump(expectedStartPosition, expectedEndingPosition, expectedBoard);
        expectedJumpedPosition = 19;
        actualJumpedPosition = jump.getJumpedPositions().get(0);
        assertEquals(expectedJumpedPosition, actualJumpedPosition);

    }

    @Test
    public void testToString() {
        int expectedStartPosition = 10;
        int expectedEndingPosition = 19;
        Board expectedBoard = new Board();
        SingleJump jump = new SingleJump(expectedStartPosition, expectedEndingPosition,
                expectedBoard);
        assertEquals("10x19", jump.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToCreateInvalidSingleJump() throws Exception {
        final Board testBoard = new Board(Arrays.asList(18, 19), Arrays.asList(23));
        SingleJump invalidJump = new SingleJump(23, 9, testBoard);
        assertNull(invalidJump);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToCreateInvalidSingleJumpWithBadEndPosition() throws Exception {
        final Board testBoard = new Board();
        SingleJump invalidJump = new SingleJump(26, 33, testBoard);
        assertNull(invalidJump);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToCreateInvalidSingleJumpWithBadStartPosition() throws Exception {
        final Board testBoard = new Board();
        SingleJump invalidJump = new SingleJump(33, 26, testBoard);
        assertNull(invalidJump);
    }
}
