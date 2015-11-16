package model.ai.evaluation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import model.Board;
import model.PieceColor;

public class GameOverEvaluatorTest {
    @Test
    public void testEvaluateBoard() {
        Board board = new Board(Arrays.asList(1), Arrays.asList());

        final BoardEvaluatorInterface gameOverEvaluator = GameOverEvaluator.getInstance();

        final double epsilon = 0.0;

        double expectedValue = 1.0;
        double actualValue = gameOverEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

        board = new Board(Arrays.asList(), Arrays.asList(15, 16, 17));
        actualValue = gameOverEvaluator.evaluateBoard(board, PieceColor.WHITE);
        expectedValue = 1;
        assertEquals(expectedValue, actualValue, epsilon);

    }
}
