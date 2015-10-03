package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<Integer> blackPositions = new ArrayList<>();
        List<Integer> whitePositions = new ArrayList<>();
        Board board = new Board(blackPositions, whitePositions);
        board.setOccupyingPiece(1, new Piece(PieceColor.BLACK));

        MoveGenerator mg = new MoveGenerator(board, PieceColor.BLACK);
        mg.determineThisPlayersOccupiedSquares(PieceColor.BLACK);
        List<MoveInterface> actualNonJumpMovesList = mg.determineNonJumpMoves();

        Set<MoveInterface> expectedNonJumpMovesSet = new HashSet<>();
        expectedNonJumpMovesSet.add(new Move(1, 5, board));
        expectedNonJumpMovesSet.add(new Move(1, 6, board));
        assertEquals(expectedNonJumpMovesSet.size(), actualNonJumpMovesList.size());

        Set<MoveInterface> actualNonJumpMovesSet = new HashSet<>();
        actualNonJumpMovesSet.addAll(actualNonJumpMovesList);
        assertEquals(expectedNonJumpMovesSet, actualNonJumpMovesSet);

        blackPositions = Arrays.asList(1, 4, 18);
        whitePositions = Arrays.asList(12, 15, 29);
        board = new Board(blackPositions, whitePositions);
        board.getPiece(18).kingMe();
        mg = new MoveGenerator(board, PieceColor.BLACK);
        mg.determineThisPlayersOccupiedSquares(PieceColor.BLACK);

        expectedNonJumpMovesSet = new HashSet<>();
        expectedNonJumpMovesSet.add(new Move(1, 5, board));
        expectedNonJumpMovesSet.add(new Move(1, 6, board));
        expectedNonJumpMovesSet.add(new Move(4, 8, board));
        expectedNonJumpMovesSet.add(new Move(18, 14, board));
        expectedNonJumpMovesSet.add(new Move(18, 22, board));
        expectedNonJumpMovesSet.add(new Move(18, 23, board));

        actualNonJumpMovesList = mg.determineNonJumpMoves();

        mg = new MoveGenerator(board, PieceColor.WHITE);
        mg.determineThisPlayersOccupiedSquares(PieceColor.WHITE);

        expectedNonJumpMovesSet = new HashSet<>();
        expectedNonJumpMovesSet.add(new Move(12, 8, board));
        expectedNonJumpMovesSet.add(new Move(15, 10, board));
        expectedNonJumpMovesSet.add(new Move(15, 11, board));
        expectedNonJumpMovesSet.add(new Move(29, 25, board));

        actualNonJumpMovesSet = new HashSet<>();
        actualNonJumpMovesSet.addAll(mg.determineNonJumpMoves());

        assertEquals(expectedNonJumpMovesSet, actualNonJumpMovesSet);
    }

    @Test
    public void testDetermineJumpMoves() {

        List<Integer> blackPositions = Arrays.asList(9, 10, 11, 12);
        List<Integer> whitePositions = Arrays.asList(13, 14, 15, 16);
        Board board = new Board(blackPositions, whitePositions);

        MoveGenerator mg = new MoveGenerator(board, PieceColor.BLACK);
        mg.determineThisPlayersOccupiedSquares(PieceColor.BLACK);

        Set<MoveInterface> expectedJumpMoves = new HashSet<>();
        expectedJumpMoves.add(new SingleJump(9, 18, board));
        expectedJumpMoves.add(new SingleJump(10, 17, board));
        expectedJumpMoves.add(new SingleJump(10, 19, board));
        expectedJumpMoves.add(new SingleJump(11, 18, board));
        expectedJumpMoves.add(new SingleJump(11, 20, board));
        expectedJumpMoves.add(new SingleJump(12, 19, board));

        Set<MoveInterface> actualJumpMoves = new HashSet<>();
        mg.determineJumpMoves();
        actualJumpMoves.addAll(mg.getMoves());

        assertEquals(expectedJumpMoves, actualJumpMoves);

        mg = new MoveGenerator(board, PieceColor.WHITE);
        mg.determineThisPlayersOccupiedSquares(PieceColor.WHITE);

        expectedJumpMoves = new HashSet<>();
        expectedJumpMoves.add(new SingleJump(13, 6, board));
        expectedJumpMoves.add(new SingleJump(14, 5, board));
        expectedJumpMoves.add(new SingleJump(14, 7, board));
        expectedJumpMoves.add(new SingleJump(15, 6, board));
        expectedJumpMoves.add(new SingleJump(15, 8, board));
        expectedJumpMoves.add(new SingleJump(16, 7, board));

        actualJumpMoves = new HashSet<>();
        mg.determineJumpMoves();
        actualJumpMoves.addAll(mg.getMoves());

        assertEquals(expectedJumpMoves, actualJumpMoves);

    }

    @Test
    public void testDetermineJumpMoves_MultiJumps() {

        List<Integer> blackPositions = Arrays.asList(6, 7, 8, 14, 15, 16);
        List<Integer> whitePositions = Arrays.asList(18);

        Board board = new Board(blackPositions, whitePositions);

        MoveGenerator mg = new MoveGenerator(board, PieceColor.WHITE);
        mg.determineThisPlayersOccupiedSquares(PieceColor.WHITE);

        Set<MoveInterface> expectedJumpMoves = new HashSet<>();
        expectedJumpMoves.add(new MultiJump(18, 4, Arrays.asList(9, 2, 11), board));
        expectedJumpMoves.add(new MultiJump(18, 20, Arrays.asList(9, 2, 11), board));
        expectedJumpMoves.add(new MultiJump(18, 18, Arrays.asList(9, 2, 11), board));
        expectedJumpMoves.add(new MultiJump(18, 4, Arrays.asList(11), board));
        expectedJumpMoves.add(new MultiJump(18, 18, Arrays.asList(11, 2, 9), board));
        expectedJumpMoves.add(new MultiJump(18, 4, Arrays.asList(11), board));

        Set<MoveInterface> actualJumpMoves = new HashSet<>();
        mg.determineJumpMoves();
        actualJumpMoves.addAll(mg.getMoves());

        assertEquals(expectedJumpMoves, actualJumpMoves);

    }

}
