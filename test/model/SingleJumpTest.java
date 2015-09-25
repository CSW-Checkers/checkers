package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

}
