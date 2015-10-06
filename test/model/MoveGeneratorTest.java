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
    public void testGetAllMoves_withKings() {
        List<Integer> blackPositions = Arrays.asList(10, 11, 13, 18, 25, 27, 30);
        List<Integer> whitePositions = Arrays.asList(1, 7, 8, 16, 22, 23, 24);
        Board board = new Board(blackPositions, whitePositions);
        board.getPiece(10).kingMe();
        board.getPiece(23).kingMe();
        board.getPiece(25).kingMe();

        Set<MoveInterface> expectedMoves = new HashSet<>();

        // expected black moves
        expectedMoves.add(new Move(10, 6, board));
        expectedMoves.add(new Move(10, 14, board));
        expectedMoves.add(new Move(10, 15, board));
        expectedMoves.add(new Move(11, 15, board));
        expectedMoves.add(new Move(13, 17, board));
        expectedMoves.add(new Move(25, 21, board));
        expectedMoves.add(new Move(25, 29, board));
        expectedMoves.add(new Move(27, 31, board));
        expectedMoves.add(new Move(27, 32, board));
        expectedMoves.add(new Move(30, 26, board));
        expectedMoves.add(new SingleJump(11, 20, board));
        expectedMoves.add(new MultiJump(10, 17, Arrays.asList(3, 12, 19, 26), board));
        expectedMoves.add(new MultiJump(10, 28, Arrays.asList(3, 12, 19), board));

        MoveGenerator mg = new MoveGenerator(board);
        Set<MoveInterface> actualMoves = mg.getAllPossibleMoves(PieceColor.BLACK);

        assertEquals(expectedMoves, actualMoves);

        // expected white moves
        expectedMoves = new HashSet<>();
        expectedMoves.add(new Move(1, 5, board));
        expectedMoves.add(new Move(1, 6, board));
        expectedMoves.add(new Move(7, 2, board));
        expectedMoves.add(new Move(7, 3, board));
        expectedMoves.add(new Move(8, 3, board));
        expectedMoves.add(new Move(8, 4, board));
        expectedMoves.add(new Move(16, 12, board));
        expectedMoves.add(new Move(22, 17, board));
        expectedMoves.add(new Move(23, 19, board));
        expectedMoves.add(new Move(23, 26, board));
        expectedMoves.add(new Move(24, 19, board));
        expectedMoves.add(new Move(24, 20, board));
        expectedMoves.add(new SingleJump(23, 14, board));
        expectedMoves.add(new SingleJump(23, 32, board));
        expectedMoves.add(new MultiJump(22, 6, Arrays.asList(15), board));

        actualMoves = mg.getAllPossibleMoves(PieceColor.WHITE);
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void testGetAllMoves_withoutKings() {

        List<Integer> blackPositions = Arrays.asList(1, 2, 4, 5, 6, 11, 16, 19, 20);
        List<Integer> whitePositions = Arrays.asList(7, 9, 12, 13, 17, 18, 24, 27, 29, 30, 31, 32);
        Board board = new Board(blackPositions, whitePositions);

        Set<MoveInterface> expectedMoves = new HashSet<>();

        // expected black moves
        expectedMoves.add(new Move(4, 8, board));
        expectedMoves.add(new Move(6, 10, board));
        expectedMoves.add(new Move(19, 23, board));
        expectedMoves.add(new Move(11, 15, board));
        expectedMoves.add(new SingleJump(19, 28, board));
        expectedMoves.add(new MultiJump(5, 21, Arrays.asList(14), board));
        expectedMoves.add(new MultiJump(5, 23, Arrays.asList(14), board));

        MoveGenerator mg = new MoveGenerator(board);
        Set<MoveInterface> actualMoves = mg.getAllPossibleMoves(PieceColor.BLACK);
        assertEquals(expectedMoves, actualMoves);

        // expected white moves
        expectedMoves = new HashSet<>();
        expectedMoves.add(new Move(7, 3, board));
        expectedMoves.add(new Move(12, 8, board));
        expectedMoves.add(new Move(17, 14, board));
        expectedMoves.add(new Move(18, 14, board));
        expectedMoves.add(new Move(18, 15, board));
        expectedMoves.add(new Move(27, 23, board));
        expectedMoves.add(new Move(29, 25, board));
        expectedMoves.add(new Move(30, 25, board));
        expectedMoves.add(new Move(30, 26, board));
        expectedMoves.add(new Move(31, 26, board));
        expectedMoves.add(new Move(32, 28, board));
        expectedMoves.add(new MultiJump(24, 8, Arrays.asList(15), board));

        actualMoves = mg.getAllPossibleMoves(PieceColor.WHITE);
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void testGetJumpMoves_MultiJumpsWithoutKings() {

        List<Integer> blackPositions = Arrays.asList(6, 7, 8, 14, 15);
        List<Integer> whitePositions = Arrays.asList(18);
        Board board = new Board(blackPositions, whitePositions);

        // expected white moves
        Set<MoveInterface> expectedJumpMoves = new HashSet<>();
        expectedJumpMoves.add(new MultiJump(18, 2, Arrays.asList(9), board));
        expectedJumpMoves.add(new MultiJump(18, 2, Arrays.asList(11), board));
        expectedJumpMoves.add(new MultiJump(18, 4, Arrays.asList(11), board));

        PieceColor currenetPlayersColor = PieceColor.WHITE;
        MoveGenerator mg = new MoveGenerator(board);

        Set<MoveInterface> actualJumpMoves = mg.getJumpMoves(currenetPlayersColor);
        assertEquals(expectedJumpMoves, actualJumpMoves);
    }

    @Test
    public void testGetJumpMoves_withoutKings() {

        List<Integer> blackPositions = Arrays.asList(9, 10, 11, 12);
        List<Integer> whitePositions = Arrays.asList(13, 14, 15, 16);
        Board board = new Board(blackPositions, whitePositions);

        // expected black moves
        Set<MoveInterface> expectedJumpMoves = new HashSet<>();
        expectedJumpMoves.add(new SingleJump(9, 18, board));
        expectedJumpMoves.add(new SingleJump(10, 17, board));
        expectedJumpMoves.add(new SingleJump(10, 19, board));
        expectedJumpMoves.add(new SingleJump(11, 18, board));
        expectedJumpMoves.add(new SingleJump(11, 20, board));
        expectedJumpMoves.add(new SingleJump(12, 19, board));

        MoveGenerator mg = new MoveGenerator(board);

        Set<MoveInterface> actualJumpMoves = mg.getJumpMoves(PieceColor.BLACK);
        assertEquals(expectedJumpMoves, actualJumpMoves);

        // expected white moves
        expectedJumpMoves = new HashSet<>();
        expectedJumpMoves.add(new SingleJump(13, 6, board));
        expectedJumpMoves.add(new SingleJump(14, 5, board));
        expectedJumpMoves.add(new SingleJump(14, 7, board));
        expectedJumpMoves.add(new SingleJump(15, 6, board));
        expectedJumpMoves.add(new SingleJump(15, 8, board));
        expectedJumpMoves.add(new SingleJump(16, 7, board));

        actualJumpMoves = mg.getJumpMoves(PieceColor.WHITE);
        assertEquals(expectedJumpMoves, actualJumpMoves);
    }

    @Test
    public void testGetNonJumpMoves_withAndWithoutKings() {

        List<Integer> blackPositions = new ArrayList<>();
        List<Integer> whitePositions = new ArrayList<>();

        // board with just one black piece
        Board board = new Board(blackPositions, whitePositions);
        board.setOccupyingPiece(1, new Piece(PieceColor.BLACK));

        MoveGenerator mg = new MoveGenerator(board);

        // expected black moves
        Set<MoveInterface> expectedNonJumpMovesSet = new HashSet<>();
        expectedNonJumpMovesSet.add(new Move(1, 5, board));
        expectedNonJumpMovesSet.add(new Move(1, 6, board));

        Set<MoveInterface> actualNonJumpMovesSet = mg.getNonJumpMoves(PieceColor.BLACK);
        assertEquals(expectedNonJumpMovesSet, actualNonJumpMovesSet);

        // board with three white pieces, three black pieces, one black king
        blackPositions = Arrays.asList(1, 4, 18);
        whitePositions = Arrays.asList(12, 15, 29);
        board = new Board(blackPositions, whitePositions);
        board.getPiece(18).kingMe();

        mg = new MoveGenerator(board);

        // expected black moves
        expectedNonJumpMovesSet = new HashSet<>();
        expectedNonJumpMovesSet.add(new Move(1, 5, board));
        expectedNonJumpMovesSet.add(new Move(1, 6, board));
        expectedNonJumpMovesSet.add(new Move(4, 8, board));
        expectedNonJumpMovesSet.add(new Move(18, 14, board));
        expectedNonJumpMovesSet.add(new Move(18, 22, board));
        expectedNonJumpMovesSet.add(new Move(18, 23, board));

        actualNonJumpMovesSet = mg.getNonJumpMoves(PieceColor.BLACK);
        assertEquals(expectedNonJumpMovesSet, actualNonJumpMovesSet);

        // expected white moves
        expectedNonJumpMovesSet = new HashSet<>();
        expectedNonJumpMovesSet.add(new Move(12, 8, board));
        expectedNonJumpMovesSet.add(new Move(15, 10, board));
        expectedNonJumpMovesSet.add(new Move(15, 11, board));
        expectedNonJumpMovesSet.add(new Move(29, 25, board));

        actualNonJumpMovesSet = mg.getNonJumpMoves(PieceColor.WHITE);
        assertEquals(expectedNonJumpMovesSet, actualNonJumpMovesSet);
    }
}
