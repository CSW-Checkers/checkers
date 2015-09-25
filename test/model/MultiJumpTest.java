package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiJumpTest {

    @Test
    public void testMultiJump() {
        Board expectedBoard = new Board();
        int expectedStartingPosition = 10;
        int expectedMiddlePosition = 19;
        int expectedEndingPosition = 26;
        ArrayList<SingleJump> expectedSubJumps = new ArrayList<SingleJump>(Arrays.asList(
                new SingleJump(expectedStartingPosition, expectedMiddlePosition, expectedBoard),
                new SingleJump(expectedMiddlePosition, expectedEndingPosition, expectedBoard)));

        MultiJump multiJump = new MultiJump(expectedStartingPosition, expectedEndingPosition,
                expectedSubJumps, expectedBoard);

        assertEquals(expectedStartingPosition, multiJump.getStartingPosition());
        assertEquals(expectedEndingPosition, multiJump.getEndingPosition());
        assertEquals(expectedSubJumps, multiJump.getSubJumps());
        assertEquals(expectedBoard, multiJump.getBoard());
        assertEquals(expectedBoard.getPiece(expectedStartingPosition), multiJump.getPiece());
        ArrayList<Integer> expectedJumpedPositions = new ArrayList<>(Arrays.asList(15, 23));
        assertEquals(expectedJumpedPositions, multiJump.getJumpedPositions());

    }

    @Test
    public void testToString() {
        Board expectedBoard = new Board();
        int expectedStartingPosition = 10;
        int expectedMiddlePosition = 19;
        int expectedEndingPosition = 26;
        ArrayList<SingleJump> expectedSubJumps = new ArrayList<SingleJump>(Arrays.asList(
                new SingleJump(expectedStartingPosition, expectedMiddlePosition, expectedBoard),
                new SingleJump(expectedMiddlePosition, expectedEndingPosition, expectedBoard)));

        MultiJump multiJump = new MultiJump(expectedStartingPosition, expectedEndingPosition,
                expectedSubJumps, expectedBoard);

        assertEquals(String.format("%dx%dx%d", expectedStartingPosition, expectedMiddlePosition,
                expectedEndingPosition), multiJump.toString());
    }

}
