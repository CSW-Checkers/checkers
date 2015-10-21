package model.ai.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.Board;
import model.PieceColor;

public class PawnCountEvaluatorTest {
    @Test
    public void testEvaluateBoard() {
        Board board = new Board();

        BoardEvaluatorInterface pawnCounter = new PawnCountEvaluator();

        double epsilon = 0.0;

        double expectedValue = 0.0;
        double actualValue = pawnCounter.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

        board.removePiece(1);
        board.removePiece(2);

        expectedValue = -2.0;
        actualValue = pawnCounter.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

        board.removePiece(32);
        board.removePiece(31);
        board.removePiece(30);
        board.removePiece(29);
        board.removePiece(28);
        board.removePiece(27);

        expectedValue = 4.0;
        actualValue = pawnCounter.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

    }
}
