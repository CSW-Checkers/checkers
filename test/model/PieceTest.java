package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PieceTest {

    private Piece blackPiece = new Piece(PieceColor.BLACK);
    private Piece whitePiece = new Piece(PieceColor.WHITE);

    @Test
    public void testIsBlack() {
        assertTrue(this.blackPiece.isBlack());
        assertFalse(this.whitePiece.isBlack());
    }

    @Test
    public void testIsNull() throws Exception {
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
}
