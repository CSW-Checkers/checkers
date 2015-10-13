package model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.PieceColor;

import java.util.ArrayList;
import java.util.Arrays;

public class PieceColorTest {

    @Test
    public void testColorValues() {
        ArrayList<PieceColor> expectedPieceColors = new ArrayList<PieceColor>(
                Arrays.asList(PieceColor.BLACK, PieceColor.WHITE));

        ArrayList<PieceColor> actualPieceColorValues = new ArrayList<PieceColor>(
                Arrays.asList(PieceColor.values()));

        assertEquals(2, actualPieceColorValues.size());

        for (PieceColor expectedColor : expectedPieceColors) {
            assertTrue(actualPieceColorValues.contains(expectedColor));
        }
    }

}
