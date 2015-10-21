package model.ai.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.Board;
import model.PieceColor;

public class KingCountEvaluatorTest {
    @Test
    public void testEvaluateBoard() {
        Board board = new Board();
        board.getPiece(1).kingMe();
        board.getPiece(2).kingMe();

        BoardEvaluator kingEvaluator = new KingCountEvaluator();

        double epsilon = 0.0;

        double expectedValue = 2.0;
        double actualValue = kingEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

    }
}
