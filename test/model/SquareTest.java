package model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.NullPiece;
import model.Piece;
import model.PieceColor;
import model.Square;

import java.util.Arrays;
import java.util.List;

public class SquareTest {

    @Test
    public void testIsInLeftTwoColumns() {
        List<Integer> leftTwoColumnPositions = Arrays.asList(1, 5, 9, 13, 17, 21, 25, 29);
        Square square;
        for (int i = 1; i <= 32; i++) {
            square = new Square(i, NullPiece.getInstance());
            if (leftTwoColumnPositions.contains(i)) {
                assertTrue("Position " + i + " should be in the left two columns.",
                        square.isInLeftTwoColumns());
            } else {
                assertFalse("Position " + i + " should NOT be in the left two columns.",
                        square.isInLeftTwoColumns());
            }
        }
    }

    @Test
    public void testIsInRightTwoColumns() {
        List<Integer> rightTwoColumnPositions = Arrays.asList(4, 8, 12, 16, 20, 24, 28, 32);
        Square square;
        for (int i = 1; i <= 32; i++) {
            square = new Square(i, NullPiece.getInstance());
            if (rightTwoColumnPositions.contains(i)) {
                assertTrue("Position " + i + " should be in the right two columns.",
                        square.isInRightTwoColumns());
            } else {
                assertFalse("Position " + i + " should NOT be in the right two columns.",
                        square.isInRightTwoColumns());
            }
        }
    }

    @Test
    public void testIsOccupied() {
        Square square = new Square(12, NullPiece.getInstance());
        assertFalse(square.isOccupied());

        square.setOccupyingPiece(new Piece(PieceColor.BLACK));
        assertTrue(square.isOccupied());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMakeIllegalSquare() throws Exception {
        Square badSquare = new Square(-1, NullPiece.getInstance());
        assertNull(badSquare);
    }

    @Test
    public void testRemoveOccupyingPiece() {
        Square square = new Square(14, new Piece(PieceColor.WHITE));
        assertTrue(square.isOccupied());
        square.removeOccupyingPiece();
        assertFalse(square.isOccupied());
    }

    @Test
    public void testSetOccupyingPiece() {
        Square square = new Square(19, NullPiece.getInstance());
        assertFalse(square.isOccupied());
        square.setOccupyingPiece(new Piece(PieceColor.BLACK));
        assertTrue(square.isOccupied());
    }

    @Test
    public void testSquare() {
        int expectedPosition = 18;
        Piece expectedPiece = new Piece(PieceColor.BLACK);
        Square square = new Square(expectedPosition, expectedPiece);

        assertEquals(expectedPosition, square.getPosition());
        assertEquals(expectedPiece, square.getOccupyingPiece());
        List<Integer> expectedAdjacentPositions = Arrays.asList(14, 15, 22, 23);

        for (int i = 1; i <= 32; i++) {
            if (expectedAdjacentPositions.contains(i)) {
                assertTrue(i + " should be an adjacent square position.",
                        square.getAdjacentPositions().contains(i));
            } else {
                assertFalse(i + " should NOT be an adjacent square position.",
                        square.getAdjacentPositions().contains(i));
            }
        }
    }

    @Test
    public void testSquareWithNullPiece() throws Exception {
        Square square1 = new Square(12, NullPiece.getInstance());
        Square square2 = new Square(square1);
        assertTrue(square2.getOccupyingPiece().isNull());
    }

    @Test
    public void testHashCode() {
        Square square1 = new Square(1, new Piece(PieceColor.BLACK));
        Square square2 = new Square(1, new Piece(PieceColor.BLACK));
        assertEquals(square1.hashCode(), square1.hashCode());
        assertEquals(square1.hashCode(), square2.hashCode());

        square2 = new Square(1, new Piece(PieceColor.WHITE));
        assertNotEquals(square1.hashCode(), square2.hashCode());

        square2 = new Square(2, new Piece(PieceColor.BLACK));
        assertNotEquals(square1.hashCode(), square2.hashCode());

        square2 = new Square(1, NullPiece.getInstance());
        assertNotEquals(square1.hashCode(), square2.hashCode());

        square1 = new Square(1, NullPiece.getInstance());
        assertEquals(square1.hashCode(), square2.hashCode());

        square2 = new Square(17, NullPiece.getInstance());
        assertNotEquals(square1.hashCode(), square2.hashCode());
    }

    @Test
    public void testEquals() {
        Square square1 = new Square(1, new Piece(PieceColor.BLACK));
        Square square2 = new Square(1, new Piece(PieceColor.BLACK));
        assertEquals(square1, square1);
        assertEquals(square1, square2);

        square2 = new Square(1, new Piece(PieceColor.WHITE));
        assertNotEquals(square1, square2);

        square2 = new Square(2, new Piece(PieceColor.BLACK));
        assertNotEquals(square1, square2);

        square2 = new Square(1, NullPiece.getInstance());
        assertNotEquals(square1, square2);

        square1 = new Square(1, NullPiece.getInstance());
        assertEquals(square1, square2);

        square2 = new Square(17, NullPiece.getInstance());
        assertNotEquals(square1, square2);
    }

}
