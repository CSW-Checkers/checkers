package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PieceTest {

    @Test
    public void testIsBlack() {
        Piece piece = new Piece(PieceColor.BLACK);
        assertTrue(piece.isBlack());

        piece = new Piece(PieceColor.WHITE);
        assertFalse(piece.isBlack());
    }

    @Test
    public void testIsSameColorAs() {
        Piece blackPiece1 = new Piece(PieceColor.BLACK);
        Piece blackPiece2 = new Piece(PieceColor.BLACK);
        Piece whitePiece1 = new Piece(PieceColor.WHITE);
        Piece whitePiece2 = new Piece(PieceColor.WHITE);

        assertTrue(blackPiece1.isSameColorAs(blackPiece2));
        assertTrue(whitePiece1.isSameColorAs(whitePiece2));
        assertFalse(blackPiece2.isSameColorAs(whitePiece1));
        assertFalse(whitePiece2.isSameColorAs(blackPiece1));
    }

    @Test
    public void testIsWhite() {
        Piece piece = new Piece(PieceColor.WHITE);
        assertTrue(piece.isWhite());

        piece = new Piece(PieceColor.BLACK);
        assertFalse(piece.isWhite());
    }

    @Test
    public void testKingMe() {
        Piece piece = new Piece(PieceColor.BLACK);
        assertFalse(piece.isKing());

        piece.kingMe();

        assertTrue(piece.isKing());
    }
}
