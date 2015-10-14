package model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import model.Board;
import model.Move;
import model.Piece;
import model.Square;

import java.util.Arrays;

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

    @Test
    public void testHashCode() {
        Board board1 = new Board();
        Move move1 = new Move(10, 15, board1);
        Move move2 = new Move(10, 15, board1);
        assertEquals(move1.hashCode(), move2.hashCode());
        assertEquals(move1.hashCode(), move1.hashCode());

        move2 = new Move(10, 15, new Board());
        assertEquals(move1.hashCode(), move2.hashCode());

        move2 = new Move(10, 15, new Board(Arrays.asList(1, 2, 3, 10), Arrays.asList(30, 31, 32)));
        assertNotEquals(move1.hashCode(), move2.hashCode());

        move2 = new Move(10, 14, board1);
        assertNotEquals(move1.hashCode(), move2.hashCode());
    }

    @Test
    public void testEquals() {
        Board board1 = new Board();
        Move move1 = new Move(10, 15, board1);
        Move move2 = new Move(10, 15, board1);
        assertEquals(move1, move2);
        assertEquals(move1, move1);

        move2 = new Move(10, 15, new Board());
        assertEquals(move1, move2);

        move2 = new Move(10, 15, new Board(Arrays.asList(1, 2, 3, 10), Arrays.asList(30, 31, 32)));
        assertNotEquals(move1, move2);

        move2 = new Move(10, 14, board1);
        assertNotEquals(move1, move2);
    }

}
