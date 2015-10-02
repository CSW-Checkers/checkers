package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MoveGeneratorTest {

    @Test
    public void testDetermineThisPlayersOccupiedSquares() {
        Board standardBoard = new Board();
        PieceColor blackPlayer = PieceColor.BLACK;
        MoveGenerator mg = new MoveGenerator(standardBoard, blackPlayer);

        // Check black player's occupied squares
        List<Square> blackPlayerSquares = mg.determineThisPlayersOccupiedSquares(blackPlayer);
        List<Integer> blackPlayerPositions = new ArrayList<>();

        for (Square blackPlayerSquare : blackPlayerSquares) {
            blackPlayerPositions.add(blackPlayerSquare.getPosition());
        }

        List<Integer> expectedBlackPositions = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));

        assertEquals(expectedBlackPositions.size(), blackPlayerPositions.size());

        for (Integer position : blackPlayerPositions) {
            assertTrue(expectedBlackPositions.contains(position));
            expectedBlackPositions.remove(position);
        }

        // Check white player's occupied squares
        PieceColor whitePlayer = PieceColor.WHITE;
        List<Square> whitePlayerSquares = mg.determineThisPlayersOccupiedSquares(whitePlayer);
        List<Integer> whitePlayerPositions = new ArrayList<>();

        for (Square whitePlayerSquare : whitePlayerSquares) {
            whitePlayerPositions.add(whitePlayerSquare.getPosition());
        }

        List<Integer> expectedWhitePositions = new ArrayList<>(
                Arrays.asList(21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32));

        assertEquals(expectedWhitePositions.size(), whitePlayerPositions.size());

        for (Integer position : whitePlayerPositions) {
            assertTrue(expectedWhitePositions.contains(position));
            expectedBlackPositions.remove(position);
        }
    }

    @Test
    public void testDetermineNonJumpMoves() {
        List<Integer> whitePositions = new ArrayList<>();
        List<Integer> blackPositions = new ArrayList<>();
        Board singlePieceBoard = new Board(whitePositions, blackPositions);
        singlePieceBoard.setOccupyingPiece(1, new Piece(PieceColor.BLACK));

        MoveGenerator mg = new MoveGenerator(singlePieceBoard, PieceColor.BLACK);
        mg.determineThisPlayersOccupiedSquares(PieceColor.BLACK);
        List<MoveInterface> nonJumpMoves = mg.determineNonJumpMoves();

        assertEquals(2, nonJumpMoves.size());

        for (MoveInterface move : nonJumpMoves) {
            assertEquals(move.getStartingPosition(), 1);
        }

    }
}
