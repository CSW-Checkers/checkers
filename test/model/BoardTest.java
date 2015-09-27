package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardTest {
    private Board board;

    @Before
    public void setUp() {
        this.board = new Board();
    }

    @Test
    public void testBoard() {
        final int expectedStartingPieceCounts = 12;
        assertEquals(expectedStartingPieceCounts, this.board.getNumberOfBlackPieces());
        assertEquals(expectedStartingPieceCounts, this.board.getNumberOfWhitePieces());
        assertNotNull(this.board.getGameState());

        for (int position = 1; position <= 32; position++) {
            if (position <= 12) {
                assertTrue(this.board.getPiece(position).isBlack());
            } else if (position > 12 && position < 21) {
                assertTrue(this.board.getPiece(position).isNull());
            } else {
                assertTrue(this.board.getPiece(position).isWhite());
            }
        }

    }

    @Test
    public void testCustomBoard() throws Exception {
        List<Integer> blackPositions = new ArrayList<>(Arrays.asList(1, 6, 31));
        List<Integer> whitePositions = new ArrayList<>(Arrays.asList(3, 4, 12, 19, 21));

        Board customBoardToTest = new Board(blackPositions, whitePositions);

        for (int position = 1; position <= 32; position++) {
            if (blackPositions.contains(position)) {
                assertTrue(customBoardToTest.getPiece(position).isBlack());
            } else if (whitePositions.contains(position)) {
                assertTrue(customBoardToTest.getPiece(position).isWhite());
            } else {
                assertTrue(customBoardToTest.getPiece(position).isNull());
            }
        }
    }

    @Test
    public void testGetSquare() {
        final int gameStateIndex = 0;
        final int boardPositionNumber = gameStateIndex + 1;
        assertEquals(this.board.getGameState().get(gameStateIndex),
                this.board.getSquare(boardPositionNumber));
    }

    @Test
    public void testMovePiece_JumpMove() {
        final List<Integer> blackPositions = Arrays.asList(15);
        final List<Integer> whitePositions = Arrays.asList(18);
        Board customBoard = new Board(blackPositions, whitePositions);

        final int jumpStartingPosition = 18;
        final int jumpedPosition = 15;
        final int jumpEndingPosition = 11;

        PieceInterface startingPositionPiece = customBoard.getPiece(jumpStartingPosition);
        PieceInterface jumpedPositionPiece = customBoard.getPiece(jumpedPosition);
        PieceInterface endingPositionPiece = customBoard.getPiece(jumpEndingPosition);

        assertTrue(startingPositionPiece.isWhite());
        assertTrue(jumpedPositionPiece.isBlack());
        assertTrue(endingPositionPiece.isNull());

        SingleJump testJump = new SingleJump(jumpStartingPosition, jumpEndingPosition, customBoard);
        customBoard.movePiece(testJump);

        startingPositionPiece = customBoard.getPiece(jumpStartingPosition);
        jumpedPositionPiece = customBoard.getPiece(jumpedPosition);
        endingPositionPiece = customBoard.getPiece(jumpEndingPosition);

        assertTrue(startingPositionPiece.isNull());
        assertTrue(jumpedPositionPiece.isNull());
        assertTrue(endingPositionPiece.isWhite());
    }

    @Test
    public void testMovePiece_MultiJumpMove() {
        final List<Integer> blackPositions = Arrays.asList(23, 15, 6);
        final List<Integer> whitePositions = Arrays.asList(26);
        Board customBoard = new Board(blackPositions, whitePositions);

        final int jumpStartingPosition = 26;
        final int intermediatePosition1 = 19;
        final int intermediatePosition2 = 10;
        final int jumpEndingPosition = 1;
        final List<Integer> jumpedPositions = Arrays.asList(23, 15, 6);
        final List<Integer> intermediatePositions = Arrays.asList(intermediatePosition1,
                intermediatePosition2);

        PieceInterface startingPositionPiece = customBoard.getPiece(jumpStartingPosition);
        PieceInterface endingPositionPiece = customBoard.getPiece(jumpEndingPosition);

        assertTrue(startingPositionPiece.isWhite());
        assertTrue(endingPositionPiece.isNull());

        ArrayList<PieceInterface> jumpedPositionPieces = customBoard.getPieces(jumpedPositions);
        for (PieceInterface jumpedPiece : jumpedPositionPieces) {
            assertTrue(jumpedPiece.isBlack());
        }

        ArrayList<PieceInterface> intermediatePositionPieces = customBoard
                .getPieces(intermediatePositions);
        for (PieceInterface intermediatePiece : intermediatePositionPieces) {
            assertTrue(intermediatePiece.isNull());
        }

        ArrayList<SingleJump> subJumps = new ArrayList<>(3);
        subJumps.add(new SingleJump(jumpStartingPosition, intermediatePosition1, customBoard));
        subJumps.add(new SingleJump(intermediatePosition1, intermediatePosition2, customBoard));
        subJumps.add(new SingleJump(intermediatePosition2, jumpEndingPosition, customBoard));
        MultiJump testMultiJump = new MultiJump(jumpStartingPosition, jumpEndingPosition, subJumps,
                customBoard);
        customBoard.movePiece(testMultiJump);

        startingPositionPiece = customBoard.getPiece(jumpStartingPosition);
        endingPositionPiece = customBoard.getPiece(jumpEndingPosition);

        assertTrue(startingPositionPiece.isNull());
        assertTrue(endingPositionPiece.isWhite());
        assertTrue(endingPositionPiece.isKing());

        jumpedPositionPieces = customBoard.getPieces(jumpedPositions);
        for (PieceInterface jumpedPiece : jumpedPositionPieces) {
            assertTrue(jumpedPiece.isNull());
        }

        intermediatePositionPieces = customBoard.getPieces(intermediatePositions);
        for (PieceInterface intermediatePiece : intermediatePositionPieces) {
            assertTrue(intermediatePiece.isNull());
        }
    }

    @Test
    public void testMovePiece_NormalMove() {
        final int startingPosition = 12;
        final int endingPosition = 16;
        PieceInterface startingPositionPiece = this.board.getPiece(startingPosition);
        PieceInterface endingPositionPiece = this.board.getPiece(endingPosition);
        assertTrue(startingPositionPiece.isBlack());
        assertFalse(startingPositionPiece.isNull());
        assertTrue(endingPositionPiece.isNull());

        Move testMove = new Move(startingPosition, endingPosition, this.board);
        this.board.movePiece(testMove);

        startingPositionPiece = this.board.getPiece(startingPosition);
        endingPositionPiece = this.board.getPiece(endingPosition);

        assertTrue(startingPositionPiece.isNull());
        assertFalse(endingPositionPiece.isNull());
        assertTrue(endingPositionPiece.isBlack());
    }

    @Test
    public void testRemovePiece() {
        final int blackPiecePosition = 12;
        assertFalse(this.board.getPiece(blackPiecePosition).isNull());
        this.board.removePiece(blackPiecePosition);
        assertTrue(this.board.getPiece(blackPiecePosition).isNull());
        assertEquals(12, this.board.getNumberOfWhitePieces());
        assertEquals(11, this.board.getNumberOfBlackPieces());

        final int whitePiecePosition = 27;
        this.board.removePiece(whitePiecePosition);
        assertTrue(this.board.getPiece(whitePiecePosition).isNull());
        assertEquals(11, this.board.getNumberOfWhitePieces());
        assertEquals(11, this.board.getNumberOfBlackPieces());
    }

    @Test
    public void testSetOccupyingPiece() throws Exception {
        int positionToSet = 13;

        assertTrue(this.board.getPiece(positionToSet).isNull());
        assertFalse(this.board.getPiece(positionToSet).isKing());

        this.board.setOccupyingPiece(positionToSet, new Piece(PieceColor.BLACK));

        assertTrue(this.board.getPiece(positionToSet).isBlack());
        assertFalse(this.board.getPiece(positionToSet).isKing());

        // Empty Board
        this.board = new Board(new ArrayList<Integer>(), new ArrayList<Integer>());
        positionToSet = 1;

        assertTrue(this.board.getPiece(positionToSet).isNull());
        assertFalse(this.board.getPiece(positionToSet).isKing());

        this.board.setOccupyingPiece(positionToSet, new Piece(PieceColor.WHITE));

        assertTrue(this.board.getPiece(positionToSet).isWhite());
        assertTrue(this.board.getPiece(positionToSet).isKing());
    }

}
