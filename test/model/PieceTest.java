package model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Piece;
import model.PieceColor;

public class PieceTest {

    private Piece blackPiece = new Piece(PieceColor.BLACK);
    private Piece whitePiece = new Piece(PieceColor.WHITE);

    @Test
    public void testIsBlack() {
        assertTrue(this.blackPiece.isBlack());
        assertFalse(this.whitePiece.isBlack());
    }

    @Test
    public void testIsNull() {
        assertFalse(this.blackPiece.isNull());
    }

    @Test
    public void testIsSameColorAs() {
        Piece blackPiece2 = new Piece(PieceColor.BLACK);
        Piece whitePiece2 = new Piece(PieceColor.WHITE);

        assertTrue(this.blackPiece.isSameColorAs(blackPiece2));
        assertTrue(this.whitePiece.isSameColorAs(whitePiece2));
        assertFalse(blackPiece2.isSameColorAs(this.whitePiece));
        assertFalse(whitePiece2.isSameColorAs(this.blackPiece));
    }

    @Test
    public void testIsWhite() {
        assertTrue(this.whitePiece.isWhite());
        assertFalse(this.blackPiece.isWhite());
    }

    @Test
    public void testKingMe() {
        assertFalse(this.blackPiece.isKing());

        this.blackPiece.kingMe();

        assertTrue(this.blackPiece.isKing());
    }

    @Test
    public void testHashCode() {
        assertEquals(this.blackPiece.hashCode(), new Piece(PieceColor.BLACK).hashCode());
        assertNotEquals(this.blackPiece.hashCode(), new Piece(PieceColor.WHITE).hashCode());

        assertEquals(this.whitePiece.hashCode(), new Piece(PieceColor.WHITE).hashCode());
        assertNotEquals(this.whitePiece.hashCode(), new Piece(PieceColor.BLACK).hashCode());

        assertEquals(this.blackPiece.hashCode(), this.blackPiece.hashCode());
        assertEquals(this.whitePiece.hashCode(), this.whitePiece.hashCode());
    }

    @Test
    public void testEquals() {
        assertTrue(this.blackPiece.equals(new Piece(PieceColor.BLACK)));
        assertFalse(this.blackPiece.equals(new Piece(PieceColor.WHITE)));

        assertTrue(this.whitePiece.equals(new Piece(PieceColor.WHITE)));
        assertFalse(this.whitePiece.equals(new Piece(PieceColor.BLACK)));

        assertTrue(this.blackPiece.equals(this.blackPiece));
        assertTrue(this.whitePiece.equals(this.whitePiece));
    }
}
