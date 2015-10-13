package model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.NullPiece;
import model.PieceColor;

public class NullPieceTest {

    private NullPiece instance = NullPiece.getInstance();

    @Test
    public void testGetColor() {
        PieceColor expectedColor = null;
        PieceColor actualColor = this.instance.getColor();
        assertEquals(expectedColor, actualColor);
    }

    @Test
    public void testGetInstance() {
        assertNotNull(this.instance);
        assertEquals(NullPiece.class, this.instance.getClass());
    }

    @Test
    public void testIsBlack() {
        assertFalse(this.instance.isBlack());
    }

    @Test
    public void testIsKing() {
        assertFalse(this.instance.isKing());
    }

    @Test
    public void testIsNull() {
        assertTrue(this.instance.isNull());
    }

    @Test
    public void testIsSameColorAs() {
        assertFalse(this.instance.isSameColorAs(this.instance));
    }

    @Test
    public void testIsWhite() {
        assertFalse(this.instance.isWhite());
    }

    @Test
    public void testKingMe() {
        this.instance.kingMe();
        assertFalse(this.instance.isKing());
    }

}
