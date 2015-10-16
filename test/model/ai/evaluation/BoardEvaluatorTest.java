package model.ai.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

import model.Board;
import model.PieceColor;

public class BoardEvaluatorTest {

    @Test
    public void testBackRowEvaluator() {
        Board board = new Board();

        BoardEvaluatorInterface backRowEvaluator = new BackRowCountEvaluator();

        double epsilon = 0.0;

        double expectedValue = 0.0;
        double actualValue = backRowEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

        board.removePiece(1);
        board.removePiece(2);

        expectedValue = -2.0;
        actualValue = backRowEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

        board.removePiece(32);
        board.removePiece(31);
        board.removePiece(30);
        board.removePiece(29);
        board.removePiece(28);
        board.removePiece(27);

        expectedValue = 2.0;
        actualValue = backRowEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);
    }

    @Test
    public void testEvaluateBoard_KingCount() {
        Board board = new Board();
        board.getPiece(1).kingMe();
        board.getPiece(2).kingMe();

        BoardEvaluatorInterface kingEvaluator = new KingCountEvaluator();

        double epsilon = 0.0;

        double expectedValue = 2.0;
        double actualValue = kingEvaluator.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

    }

    @Test
    public void testEvaluateBoard_PawnAndKingCount() {
        Board board = new Board(Arrays.asList(1, 2, 3, 4), Arrays.asList(30));
        board.getPiece(1).kingMe();
        board.getPiece(2).kingMe();

        BoardEvaluatorAggregator boardAgg = new BoardEvaluatorSummator();
        boardAgg.addBoardEvaluator(new KingCountEvaluator());
        boardAgg.addBoardEvaluator(new PawnCountEvaluator());

        double epsilon = 0.0;

        double expectedValue = 5.0;
        double actualValue = boardAgg.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

    }

    @Test
    public void testEvaluateBoard_PawnCount() {
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

    @Test
    public void testPawnKingAndBackRowCountEvaluator() {
        Board board = new Board();
        board.getPiece(1).kingMe();
        board.getPiece(2).kingMe();
        board.removePiece(32);

        BoardEvaluatorAggregator boardAgg = new BoardEvaluatorSummator();

        BoardEvaluator kingCounter = new KingCountEvaluator();
        BoardEvaluator pawnCounter = new PawnCountEvaluator();
        BoardEvaluator backRowCounter = new BackRowCountEvaluator();

        boardAgg.addBoardEvaluator(kingCounter);
        boardAgg.addBoardEvaluator(pawnCounter);
        boardAgg.addBoardEvaluator(backRowCounter);

        double epsilon = 0.0;

        double expectedValue = 4.0;
        double actualValue = boardAgg.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

        // change weights
        kingCounter.setWeight(2.0);
        pawnCounter.setWeight(.75);
        backRowCounter.setWeight(.5);
        expectedValue = 5.25;
        actualValue = boardAgg.evaluateBoard(board, PieceColor.BLACK);
        assertEquals(expectedValue, actualValue, epsilon);

    }

}
