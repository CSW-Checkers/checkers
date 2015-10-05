package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveGeneratorTest {

    @Test
    public void testDetermineJumpMoves() {

        List<Integer> blackPositions = Arrays.asList(9, 10, 11, 12);
        List<Integer> whitePositions = Arrays.asList(13, 14, 15, 16);
        Board board = new Board(blackPositions, whitePositions);

        Set<MoveInterface> expectedJumpMoves = new HashSet<>();
        expectedJumpMoves.add(new SingleJump(9, 18, board));
        expectedJumpMoves.add(new SingleJump(10, 17, board));
        expectedJumpMoves.add(new SingleJump(10, 19, board));
        expectedJumpMoves.add(new SingleJump(11, 18, board));
        expectedJumpMoves.add(new SingleJump(11, 20, board));
        expectedJumpMoves.add(new SingleJump(12, 19, board));

        PieceColor currentPlayersColor = PieceColor.BLACK;
        MoveGenerator mg = new MoveGenerator(board);

        Set<MoveInterface> actualJumpMoves = mg.getJumpMoves(currentPlayersColor);
        assertEquals(expectedJumpMoves, actualJumpMoves);

        currentPlayersColor = PieceColor.WHITE;

        expectedJumpMoves = new HashSet<>();
        expectedJumpMoves.add(new SingleJump(13, 6, board));
        expectedJumpMoves.add(new SingleJump(14, 5, board));
        expectedJumpMoves.add(new SingleJump(14, 7, board));
        expectedJumpMoves.add(new SingleJump(15, 6, board));
        expectedJumpMoves.add(new SingleJump(15, 8, board));
        expectedJumpMoves.add(new SingleJump(16, 7, board));

        actualJumpMoves = mg.getJumpMoves(currentPlayersColor);
        assertEquals(expectedJumpMoves, actualJumpMoves);
    }

    @Test
    public void testDetermineJumpMoves_MultiJumps() {

        List<Integer> blackPositions = Arrays.asList(6, 7, 8, 14, 15, 16);
        List<Integer> whitePositions = Arrays.asList(18);

        Board board = new Board(blackPositions, whitePositions);

        Set<MoveInterface> expectedJumpMoves = new HashSet<>();
        expectedJumpMoves.add(new MultiJump(18, 4, Arrays.asList(9, 2, 11), board));
        expectedJumpMoves.add(new MultiJump(18, 20, Arrays.asList(9, 2, 11), board));
        expectedJumpMoves.add(new MultiJump(18, 18, Arrays.asList(9, 2, 11), board));
        expectedJumpMoves.add(new MultiJump(18, 4, Arrays.asList(11), board));
        expectedJumpMoves.add(new MultiJump(18, 18, Arrays.asList(11, 2, 9), board));
        expectedJumpMoves.add(new MultiJump(18, 4, Arrays.asList(11), board));

        PieceColor currenetPlayersColor = PieceColor.WHITE;
        MoveGenerator mg = new MoveGenerator(board);

        Set<MoveInterface> actualJumpMoves = mg.getJumpMoves(currenetPlayersColor);

        assertEquals(expectedJumpMoves, actualJumpMoves);
    }

    @Test
    public void testDetermineNonJumpMoves() {

        List<Integer> blackPositions = new ArrayList<>();
        List<Integer> whitePositions = new ArrayList<>();
        PieceColor currentPlayersColor = PieceColor.BLACK;

        Board board = new Board(blackPositions, whitePositions);
        board.setOccupyingPiece(1, new Piece(currentPlayersColor));

        MoveGenerator mg = new MoveGenerator(board);

        Set<MoveInterface> actualNonJumpMovesSet = mg.getNonJumpMoves(currentPlayersColor);

        Set<MoveInterface> expectedNonJumpMovesSet = new HashSet<>();
        expectedNonJumpMovesSet.add(new Move(1, 5, board));
        expectedNonJumpMovesSet.add(new Move(1, 6, board));

        assertEquals(expectedNonJumpMovesSet, actualNonJumpMovesSet);

        blackPositions = Arrays.asList(1, 4, 18);
        whitePositions = Arrays.asList(12, 15, 29);
        board = new Board(blackPositions, whitePositions);
        board.getPiece(18).kingMe();
        mg = new MoveGenerator(board);

        expectedNonJumpMovesSet = new HashSet<>();
        expectedNonJumpMovesSet.add(new Move(1, 5, board));
        expectedNonJumpMovesSet.add(new Move(1, 6, board));
        expectedNonJumpMovesSet.add(new Move(4, 8, board));
        expectedNonJumpMovesSet.add(new Move(18, 14, board));
        expectedNonJumpMovesSet.add(new Move(18, 22, board));
        expectedNonJumpMovesSet.add(new Move(18, 23, board));

        actualNonJumpMovesSet = mg.getNonJumpMoves(currentPlayersColor);
        assertEquals(expectedNonJumpMovesSet, actualNonJumpMovesSet);

        currentPlayersColor = PieceColor.WHITE;

        expectedNonJumpMovesSet = new HashSet<>();
        expectedNonJumpMovesSet.add(new Move(12, 8, board));
        expectedNonJumpMovesSet.add(new Move(15, 10, board));
        expectedNonJumpMovesSet.add(new Move(15, 11, board));
        expectedNonJumpMovesSet.add(new Move(29, 25, board));

        actualNonJumpMovesSet = mg.getNonJumpMoves(currentPlayersColor);
    }
}
