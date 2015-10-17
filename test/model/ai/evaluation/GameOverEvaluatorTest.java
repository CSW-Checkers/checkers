package model.ai.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

import model.Board;
import model.PieceColor;

public class GameOverEvaluatorTest {
    @Test
    public void testEvaluateBoard() {
        Board board = new Board(Arrays.asList(1), Arrays.asList());

        BoardEvaluator gameOverEvaluator = new GameOverEvaluator();

        double epsilon = 0.0;

        double expectedValue = 1.0;
        double actualValue = gameOverEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

        board = new Board(Arrays.asList(), Arrays.asList(15, 16, 17));
        gameOverEvaluator.setWeight(100);
        actualValue = gameOverEvaluator.evaluateBoard(board, PieceColor.WHITE);
        expectedValue = 100;
        assertEquals(expectedValue, actualValue, epsilon);

    }
}
