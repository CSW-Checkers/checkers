package model.ai.evaluation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import model.Board;
import model.PieceColor;
import model.Strategy;

public class BoardEvaluatorSummatorTest {

    @Test
    public void testEvaluateBoard_GameOverAndPawnCount() {
        final Board board1 = new Board(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                Arrays.asList(32));

        final BoardEvaluatorAggregator boardAgg = new BoardEvaluatorSummator();
        final HashMap<BoardEvaluatorInterface, Double> evaluatorWeightMap = new HashMap<>();
        evaluatorWeightMap.put(GameOverEvaluator.getInstance(), 100.0);
        evaluatorWeightMap.put(PawnCountEvaluator.getInstance(), 1.0);

        final Strategy strategy = new Strategy(boardAgg, PieceColor.BLACK, evaluatorWeightMap);

        final double epsilon = 0.0;

        final double expectedValue1 = 9.0;
        final double actualValue1 = boardAgg.evaluateBoard(strategy, board1);
        assertEquals(expectedValue1, actualValue1, epsilon);

        final Board board2 = new Board(Arrays.asList(), Arrays.asList(8));

        final double actualValue2 = boardAgg.evaluateBoard(strategy, board2);
        final double expectedValue2 = -101.0;
        assertEquals(expectedValue2, actualValue2, epsilon);
    }

    @Test
    public void testEvaluateBoard_PawnAndKingCount() {
        final Board board = new Board(Arrays.asList(1, 2, 3, 4), Arrays.asList(30));
        board.getPiece(1).kingMe();
        board.getPiece(2).kingMe();

        final BoardEvaluatorAggregator boardAgg = new BoardEvaluatorSummator();
        final HashMap<BoardEvaluatorInterface, Double> evaluatorWeightMap = new HashMap<>();
        evaluatorWeightMap.put(KingCountEvaluator.getInstance(), 1.0);
        evaluatorWeightMap.put(PawnCountEvaluator.getInstance(), 1.0);

        final Strategy strategy = new Strategy(boardAgg, PieceColor.BLACK, evaluatorWeightMap);

        final double epsilon = 0.0;

        final double expectedValue = 3.0;
        final double actualValue = boardAgg.evaluateBoard(strategy, board);
        assertEquals(expectedValue, actualValue, epsilon);

    }

    @Test
    public void testEvaluateBoard_PawnCountAndKingCountAndBackRowCount() {
        final Board board = new Board();
        board.getPiece(1).kingMe();
        board.getPiece(2).kingMe();
        board.removePiece(32);
        board.updateCountsInMaps();

        final BoardEvaluatorAggregator boardAgg = new BoardEvaluatorSummator();
        HashMap<BoardEvaluatorInterface, Double> evaluatorWeightMap = new HashMap<>();
        evaluatorWeightMap.put(KingCountEvaluator.getInstance(), 2.0);
        evaluatorWeightMap.put(PawnCountEvaluator.getInstance(), 1.0);
        evaluatorWeightMap.put(BackRowCountEvaluator.getInstance(), 1.0);

        Strategy strategy = new Strategy(boardAgg, PieceColor.BLACK, evaluatorWeightMap);

        final double epsilon = 0.0;

        double expectedValue = 4.0;
        double actualValue = boardAgg.evaluateBoard(strategy, board);
        assertEquals(expectedValue, actualValue, epsilon);

        // change weights
        evaluatorWeightMap = new HashMap<>();
        evaluatorWeightMap.put(KingCountEvaluator.getInstance(), 1.5);
        evaluatorWeightMap.put(PawnCountEvaluator.getInstance(), 0.75);
        evaluatorWeightMap.put(BackRowCountEvaluator.getInstance(), 0.5);

        strategy = new Strategy(boardAgg, PieceColor.BLACK, evaluatorWeightMap);

        expectedValue = 2.75;
        actualValue = boardAgg.evaluateBoard(strategy, board);
        assertEquals(expectedValue, actualValue, epsilon);

    }

}
