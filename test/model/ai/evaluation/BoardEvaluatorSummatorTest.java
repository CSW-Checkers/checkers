package model.ai.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

import model.Board;
import model.PieceColor;

public class BoardEvaluatorSummatorTest {

    @Test
    public void testEvaluateBoard_GameOverAndPawnCount() {
        Board board1 = new Board(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), Arrays.asList(32));

        BoardEvaluatorAggregator boardAgg = new BoardEvaluatorSummator();
        BoardEvaluator gameOverEvaluator = new GameOverEvaluator(100.0);
        BoardEvaluator pawnCounter = new PawnCountEvaluator();
        boardAgg.addBoardEvaluator(gameOverEvaluator);
        boardAgg.addBoardEvaluator(pawnCounter);

        double epsilon = 0.0;

        double expectedValue1 = 9.0;
        double actualValue1 = boardAgg.evaluateBoard(board1, PieceColor.BLACK);
        assertEquals(expectedValue1, actualValue1, epsilon);

        Board board2 = new Board(Arrays.asList(), Arrays.asList(1));

        double actualValue2 = boardAgg.evaluateBoard(board2, PieceColor.WHITE);
        double expectedValue2 = 101.0;
        assertEquals(expectedValue2, actualValue2, epsilon);
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
    public void testEvaluateBoard_PawnCountAndKingCountAndBackRowCount() {
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
