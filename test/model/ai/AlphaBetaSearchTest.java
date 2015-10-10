package model.ai;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

import model.Board;
import model.MoveInterface;
import model.PieceColor;
import model.SingleJump;

public class AlphaBetaSearchTest {

    @Test
    public void testGetBestMove_SimpleGameState_Piece() {

        Board board = new Board(Arrays.asList(1, 2, 3, 4, 12), Arrays.asList(29, 30, 31, 32, 16));
        AlphaBetaSearch searcher = new AlphaBetaSearch(board, PieceColor.BLACK,
                new PieceCountEvaluator(), 1);

        MoveInterface expectedBestMove = new SingleJump(12, 19, board);
        MoveInterface actualBestMove = searcher.alphaBetaSearch();

        assertEquals(expectedBestMove, actualBestMove);

    }

}
