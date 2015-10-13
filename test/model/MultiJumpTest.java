package model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import model.Board;
import model.MultiJump;
import model.SingleJump;

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

    @Test
    public void testHashCode() {
        Board board1 = new Board(Arrays.asList(6, 7), Arrays.asList(10, 18, 19, 26));
        MultiJump multiJump1 = new MultiJump(6, 31, Arrays.asList(15, 22), board1);
        MultiJump multiJump2 = new MultiJump(6, 31, Arrays.asList(15, 22), board1);
        assertEquals(multiJump1.hashCode(), multiJump1.hashCode());
        assertEquals(multiJump1.hashCode(), multiJump2.hashCode());

        multiJump2 = new MultiJump(6, 31, Arrays.asList(15, 22), new Board(board1));

        multiJump2 = new MultiJump(6, 31, Arrays.asList(15, 24), board1);
        assertNotEquals(multiJump1.hashCode(), multiJump2.hashCode());
    }

    @Test
    public void testEquals() {
        Board board1 = new Board(Arrays.asList(6, 7), Arrays.asList(10, 18, 19, 26));
        MultiJump multiJump1 = new MultiJump(6, 31, Arrays.asList(15, 22), board1);
        MultiJump multiJump2 = new MultiJump(6, 31, Arrays.asList(15, 22), board1);
        assertEquals(multiJump1, multiJump1);
        assertEquals(multiJump1, multiJump2);
        assertEquals(multiJump2, multiJump1);

        multiJump2 = new MultiJump(6, 31, Arrays.asList(15, 22), new Board(board1));

        multiJump2 = new MultiJump(6, 31, Arrays.asList(15, 24), board1);
        assertNotEquals(multiJump1, multiJump2);

    }

}
