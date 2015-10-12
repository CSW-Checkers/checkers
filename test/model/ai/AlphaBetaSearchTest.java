package model.ai;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

import model.Board;
import model.MoveInterface;
import model.MultiJump;
import model.PieceColor;
import model.SingleJump;

public class AlphaBetaSearchTest {

    @Test
    public void testGetBestMove_PieceCountEvaluator_LightComplexity() {
        Board board = new Board(Arrays.asList(1, 10, 11), Arrays.asList(14, 15, 22, 25));
        AlphaBetaSearch searcher = new AlphaBetaSearch(board, PieceColor.BLACK,
                new PieceCountEvaluator(), 4);

        MoveInterface expectedBestMove = new MultiJump(10, 26, Arrays.asList(17), board);
        MoveInterface actualBestMove = searcher.alphaBetaSearch();

        assertEquals(expectedBestMove, actualBestMove);
    }

    @Test
    public void testGetBestMove_PieceCountEvaluator_MaximumComplexity() {
        Board board = new Board(Arrays.asList(1, 6, 4, 9, 10, 11, 13, 16, 23, 32),
                Arrays.asList(7, 14, 15, 22, 24, 25, 26, 27, 30, 31));
        board.getPiece(7).kingMe();
        board.getPiece(23).kingMe();
        board.getPiece(32).kingMe();
        board.getPiece(14).kingMe();

        AlphaBetaSearch searcher = new AlphaBetaSearch(board, PieceColor.BLACK,
                new PieceCountEvaluator(), 6);

        MoveInterface expectedBestMove = new MultiJump(10, 28, Arrays.asList(19), board);
        MoveInterface actualBestMove = searcher.alphaBetaSearch();

        assertEquals(expectedBestMove, actualBestMove);
    }

    @Test
    public void testGetBestMove_PieceCountEvaluator_MediumComplexity() {
        Board board = new Board(Arrays.asList(1, 4, 9, 10, 11, 16, 23),
                Arrays.asList(7, 14, 15, 20, 22, 25, 26, 27));
        board.getPiece(7).kingMe();
        board.getPiece(23).kingMe();

        AlphaBetaSearch searcher = new AlphaBetaSearch(board, PieceColor.BLACK,
                new PieceCountEvaluator(), 4);

        MoveInterface expectedBestMove = new MultiJump(23, 21, Arrays.asList(30), board);
        MoveInterface actualBestMove = searcher.alphaBetaSearch();

        assertEquals(expectedBestMove, actualBestMove);
    }

    @Test
    public void testGetBestMove_PieceCountEvaluator_NoComplexity() {

        Board board = new Board(Arrays.asList(1, 2, 3, 4, 12), Arrays.asList(29, 30, 31, 32, 16));
        AlphaBetaSearch searcher = new AlphaBetaSearch(board, PieceColor.BLACK,
                new PieceCountEvaluator(), 1);

        MoveInterface expectedBestMove = new SingleJump(12, 19, board);
        MoveInterface actualBestMove = searcher.alphaBetaSearch();

        assertEquals(expectedBestMove, actualBestMove);

    }

}
