package model.ai;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

import model.Board;
import model.PieceColor;

public class BoardEvaluatorTest {

    @Test
    public void testEvaluateBoard_KingCount() {
        Board board = new Board();
        board.getPiece(1).kingMe();
        board.getPiece(2).kingMe();

        BoardEvaluatorInterface boardEvaluator = new KingCountEvaluator(new PlainBoardEvaluator());

        double epsilon = 0.0;

        double expectedValue = 2.0;
        double actualValue = boardEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

    }

    @Test
    public void testEvaluateBoard_PawnAndKingCount() {
        Board board = new Board(Arrays.asList(1, 2, 3, 4), Arrays.asList(30));
        board.getPiece(1).kingMe();
        board.getPiece(2).kingMe();

        BoardEvaluatorInterface boardEvaluator = new KingCountEvaluator(
                new PawnCountEvaluator(new PlainBoardEvaluator()));

        double epsilon = 0.0;

        double expectedValue = 5.0;
        double actualValue = boardEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

    }

    @Test
    public void testEvaluateBoard_PawnCount() {
        Board board = new Board();
        BoardEvaluatorInterface boardEvaluator = new PawnCountEvaluator(new PlainBoardEvaluator());

        double epsilon = 0.0;

        double expectedValue = 0.0;
        double actualValue = boardEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

        board.removePiece(1);
        board.removePiece(2);

        expectedValue = -2.0;
        actualValue = boardEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

        board.removePiece(32);
        board.removePiece(31);
        board.removePiece(30);
        board.removePiece(29);
        board.removePiece(28);
        board.removePiece(27);

        expectedValue = 4.0;
        actualValue = boardEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

    }

}
