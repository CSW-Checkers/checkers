package model.ai.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.Board;
import model.PieceColor;

public class KingCountEvaluatorTest {
    @Test
    public void testEvaluateBoard() {
        final Board board = new Board();
        board.getPiece(1).kingMe();
        board.getPiece(2).kingMe();
        board.updateCountsInMaps();

        final BoardEvaluatorInterface kingEvaluator = KingCountEvaluator.getInstance();

        final double epsilon = 0.0;

        final double expectedValue = 2.0;
        final double actualValue = kingEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

    }
}
