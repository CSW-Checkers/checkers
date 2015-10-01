package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiJumpTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMakeBadMultiJump() throws Exception {
        MultiJump badMultiJump = new MultiJump(-1, 12, null, null);
        assertNull(badMultiJump);
    }

    @Test
    public void testMultiJump() {
        int expectedStartingPosition = 10;
        Board expectedBoard = new Board(Arrays.asList(expectedStartingPosition),
                Arrays.asList(15, 23));
        int expectedMiddlePosition = 19;
        int expectedEndingPosition = 26;
        ArrayList<Integer> intermediatePositions = new ArrayList<Integer>(
                Arrays.asList(expectedMiddlePosition));
        MultiJump multiJump = new MultiJump(expectedStartingPosition, expectedEndingPosition,
                intermediatePositions, expectedBoard);
        ArrayList<SingleJump> expectedSubJumps = new ArrayList<SingleJump>(Arrays.asList(
                new SingleJump(expectedStartingPosition, expectedMiddlePosition, expectedBoard),
                new SingleJump(expectedMiddlePosition, expectedEndingPosition, expectedBoard)));

        assertEquals(expectedStartingPosition, multiJump.getStartingPosition());
        assertEquals(expectedEndingPosition, multiJump.getEndingPosition());
        assertEquals(expectedSubJumps.get(0).toString(), multiJump.getSubJumps().get(0).toString());
        assertEquals(expectedSubJumps.get(1).toString(), multiJump.getSubJumps().get(1).toString());
        assertEquals(expectedBoard, multiJump.getBoard());
        assertEquals(expectedBoard.getPiece(expectedStartingPosition), multiJump.getPiece());
        ArrayList<Integer> expectedJumpedPositions = new ArrayList<>(Arrays.asList(15, 23));
        assertEquals(expectedJumpedPositions, multiJump.getJumpedPositions());

    }

    @Test
    public void testToString() {
        int expectedStartingPosition = 10;
        Board expectedBoard = new Board(Arrays.asList(expectedStartingPosition),
                Arrays.asList(15, 23));
        int expectedMiddlePosition = 19;
        int expectedEndingPosition = 26;
        ArrayList<Integer> intermediatePositions = new ArrayList<Integer>(
                Arrays.asList(expectedMiddlePosition));

        MultiJump multiJump = new MultiJump(expectedStartingPosition, expectedEndingPosition,
                intermediatePositions, expectedBoard);

        assertEquals(String.format("%dx%dx%d", expectedStartingPosition, expectedMiddlePosition,
                expectedEndingPosition), multiJump.toString());
    }

}
