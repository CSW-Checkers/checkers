package model.ai;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

import model.Board;
import model.Move;
import model.MoveInterface;
import model.MultiJump;
import model.PieceColor;
import model.SingleJump;

public class AlphaBetaSearchTest {

    @Test
    public void testGetBestMove_PieceCountEvaluator_LightComplexity() {
        Board board = new Board(Arrays.asList(1, 7, 10, 11), Arrays.asList(14, 16, 22, 25));
        AlphaBetaSearch searcher = new AlphaBetaSearch(board, PieceColor.BLACK,
                new PieceCountEvaluator(), 8);

        MoveInterface expectedBestMove = new MultiJump(10, 26, Arrays.asList(17), board);
        MoveInterface actualBestMove = searcher.alphaBetaSearch();

        assertEquals(expectedBestMove, actualBestMove);
    }

    @Test(timeout = 5000)
    public void testGetBestMove_PieceCountEvaluator_ManyKingsHighBranchFactor() {
        Board board = new Board(Arrays.asList(5, 6, 7, 8), Arrays.asList(25, 26, 27, 28));
        board.getPiece(5).kingMe();
        board.getPiece(6).kingMe();
        board.getPiece(7).kingMe();
        board.getPiece(8).kingMe();
        board.getPiece(25).kingMe();
        board.getPiece(26).kingMe();
        board.getPiece(27).kingMe();
        board.getPiece(28).kingMe();
        AlphaBetaSearch searcher = new AlphaBetaSearch(board, PieceColor.BLACK,
                new PieceCountEvaluator(), 8);

        searcher.alphaBetaSearch();
    }

    @Test
    public void testGetBestMove_PieceCountEvaluator_MaximumComplexity() {
        Board board = new Board(Arrays.asList(1, 4, 6, 9, 10, 11, 13, 16, 23, 32),
                Arrays.asList(7, 14, 15, 22, 24, 25, 26, 27, 30, 31));
        board.getPiece(7).kingMe();
        board.getPiece(23).kingMe();
        board.getPiece(32).kingMe();
        board.getPiece(14).kingMe();

        AlphaBetaSearch searcher = new AlphaBetaSearch(board, PieceColor.BLACK,
                new PieceCountEvaluator(), 8);

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
                new PieceCountEvaluator(), 8);

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

    @Test
    public void testGetMove_baitTheTripleJump() {
        Board board = new Board(Arrays.asList(1, 6, 9), Arrays.asList(11, 17, 19, 27));
        AlphaBetaSearch searcher = new AlphaBetaSearch(board, PieceColor.BLACK,
                new PieceCountEvaluator(), 3);

        MoveInterface expectedBestMove = new Move(9, 14, board);
        MoveInterface actualBestMove = searcher.alphaBetaSearch();

        assertEquals(expectedBestMove, actualBestMove);
    }
}
