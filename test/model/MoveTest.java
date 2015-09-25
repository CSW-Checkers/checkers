package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MoveTest {

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
