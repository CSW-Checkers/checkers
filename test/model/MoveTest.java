package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MoveTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateBadMove() throws Exception {
        Move badMove = new Move(-1, 27, null);
        assertNull(badMove);
    }

    @Test
    public void testGetEndingSquare() {
        int expectedStartingPosition = 10;
        int expectedEndingPosition = 15;
        Board expectedBoard = new Board();
        Move move = new Move(expectedStartingPosition, expectedEndingPosition, expectedBoard);

        Square expectedSquare = expectedBoard.getSquare(expectedEndingPosition);
        assertEquals(expectedSquare, move.getEndingSquare());
    }

    @Test
    public void testGetStartingSquare() {
        int expectedStartingPosition = 10;
        int expectedEndingPosition = 15;
        Board expectedBoard = new Board();
        Move move = new Move(expectedStartingPosition, expectedEndingPosition, expectedBoard);

        Square expectedSquare = expectedBoard.getSquare(expectedStartingPosition);
        assertEquals(expectedSquare, move.getStartingSquare());
    }

    @Test
    public void testMove() {
        int expectedStartingPosition = 10;
        int expectedEndingPosition = 15;
        Board expectedBoard = new Board();
        Move move = new Move(expectedStartingPosition, expectedEndingPosition, expectedBoard);

        assertEquals(expectedStartingPosition, move.getStartingPosition());
        assertEquals(expectedEndingPosition, move.getEndingPosition());
        assertEquals(expectedBoard, move.getBoard());
        assertEquals(Piece.class, move.getPiece().getClass());
    }

    @Test
    public void testToString() {
        Move move = new Move(10, 15, new Board());
        assertEquals("10-15", move.toString());
    }

}
