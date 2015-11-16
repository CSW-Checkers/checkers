package model.ai.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

import model.Board;
import model.PieceColor;

public class PawnDistanceToKingedEvaluatorTest {

    Board board;
    BoardEvaluatorInterface evaluator;

    @Test
    public void testEvaluator() {
        this.board = new Board();
        this.evaluator = PawnDistanceToKingedEvaluator.getInstance();
        assertEquals(0.0, this.evaluator.evaluateBoard(this.board, PieceColor.BLACK), 0.0);

        this.board = new Board(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 16),
                Arrays.asList(17, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32));
        assertEquals(1.0, this.evaluator.evaluateBoard(this.board, PieceColor.BLACK), 0.0);

        assertEquals(1.0, this.evaluator.evaluateBoard(this.board, PieceColor.BLACK), 0.0);
        assertEquals(-1.0, this.evaluator.evaluateBoard(this.board, PieceColor.WHITE), 0.0);

        this.board = new Board(Arrays.asList(1), Arrays.asList(13, 21, 29));
        assertEquals(8.0, this.evaluator.evaluateBoard(this.board, PieceColor.WHITE), 0.0);

    }
}
