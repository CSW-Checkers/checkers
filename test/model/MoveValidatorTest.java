package model;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Board;
import model.Move;
import model.MoveValidator;
import model.MultiJump;
import model.SingleJump;

import java.util.ArrayList;
import java.util.Arrays;

public class MoveValidatorTest {

    @Test
    public void testCanOnlyJumpIntoCorrectSquares() {
        final Board testBoard = new Board(Arrays.asList(18, 19), Arrays.asList(23));

        SingleJump validJump = new SingleJump(23, 14, testBoard);
        assertTrue(MoveValidator.isValidMove(validJump));

        validJump = new SingleJump(23, 16, testBoard);
        assertTrue(MoveValidator.isValidMove(validJump));
    }

    @Test
    public void testCanOnlyMoveToAdjacentSquare() {
        final Board testBoard = new Board(new ArrayList<>(), Arrays.asList(23));

        Move testValidMove = new Move(23, 18, testBoard);
        assertTrue(MoveValidator.isValidMove(testValidMove));

        testValidMove = new Move(23, 19, testBoard);
        assertTrue(MoveValidator.isValidMove(testValidMove));

        Move testInvalidMove = new Move(23, 11, testBoard);
        assertFalse(MoveValidator.isValidMove(testInvalidMove));

        testInvalidMove = new Move(23, 9, testBoard);
        assertFalse(MoveValidator.isValidMove(testInvalidMove));
    }

    @Test
    public void testIllegalBackwardsMove() {
        final Board testBoard = new Board(new ArrayList<>(), Arrays.asList(23));

        Move backwardsMove = new Move(23, 26, testBoard);
        assertFalse(MoveValidator.isValidMove(backwardsMove));

        backwardsMove = new Move(23, 27, testBoard);
        assertFalse(MoveValidator.isValidMove(backwardsMove));

        backwardsMove = new Move(23, 25, testBoard);
        assertFalse(MoveValidator.isValidMove(backwardsMove));
    }

    @Test
    public void testIsOnBoard() {
        assertFalse(MoveValidator.isOnBoard(0));
        for (int position = 1; position <= 32; position++) {
            assertTrue(MoveValidator.isOnBoard(position));
        }
        assertFalse(MoveValidator.isOnBoard(33));
    }

    @Test
    public void testJumpIntoOccupiedSquare() {
        final Board testBoard = new Board(Arrays.asList(18, 19, 14), Arrays.asList(23, 16));

        SingleJump invalidJump = new SingleJump(23, 14, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));

        invalidJump = new SingleJump(23, 16, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));
    }

    @Test
    public void testJumpOffLeftEdgeOfBoardTowardsBlack() {
        final Board testBoard = new Board(Arrays.asList(13, 16), Arrays.asList(21, 17));

        SingleJump invalidJump = new SingleJump(17, 8, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));

        invalidJump = new SingleJump(21, 12, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));
    }

    @Test
    public void testJumpOffLeftEdgeOfBoardTowardsWhite() {
        final Board testBoard = new Board(Arrays.asList(9, 15), Arrays.asList(13, 8));

        SingleJump invalidJump = new SingleJump(9, 16, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));

        invalidJump = new SingleJump(5, 12, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));
    }

    @Test
    public void testJumpOffRightEdgeOfBoardTowardsBlack() {
        final Board testBoard = new Board(Arrays.asList(12, 17), Arrays.asList(16, 20));

        SingleJump invalidJump = new SingleJump(16, 9, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));

        invalidJump = new SingleJump(20, 13, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));
    }

    @Test
    public void testJumpOffRightEdgeOfBoardTowardsWhite() {
        final Board testBoard = new Board(Arrays.asList(12, 16), Arrays.asList(17, 20));

        SingleJump invalidJump = new SingleJump(16, 25, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));

        invalidJump = new SingleJump(12, 21, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));
    }

    @Test
    public void testJumpOverEmptySquare() {
        final Board testBoard = new Board(new ArrayList<>(), Arrays.asList(23));
        SingleJump invalidJump = new SingleJump(23, 14, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));

        invalidJump = new SingleJump(23, 16, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidJump));
    }

    @Test
    public void testKingCanDoAMultiJumpWithBothDirections() {
        final int startingPosition = 21;
        final Board testBoard = new Board(Arrays.asList(17, 18, 19),
                Arrays.asList(startingPosition));

        testBoard.getPiece(startingPosition).kingMe();

        assertTrue(testBoard.getPiece(startingPosition).isKing());

        ArrayList<Integer> subJumps = new ArrayList<Integer>(Arrays.asList(14, 23));

        MultiJump multiJump = new MultiJump(startingPosition, 16, subJumps, testBoard);
        assertTrue(MoveValidator.isValidMove(multiJump));
    }

    @Test
    public void testKingCanJumpBothDirections() {
        final int startingPosition = 23;
        final Board testBoard = new Board(Arrays.asList(18, 19, 26, 27),
                Arrays.asList(startingPosition));
        testBoard.getPiece(startingPosition).kingMe();

        SingleJump forwardJump = new SingleJump(startingPosition, 30, testBoard);
        assertTrue(MoveValidator.isValidMove(forwardJump));

        forwardJump = new SingleJump(startingPosition, 32, testBoard);
        assertTrue(MoveValidator.isValidMove(forwardJump));

        SingleJump backwardsJump = new SingleJump(startingPosition, 14, testBoard);
        assertTrue(MoveValidator.isValidMove(backwardsJump));

        backwardsJump = new SingleJump(startingPosition, 16, testBoard);
        assertTrue(MoveValidator.isValidMove(backwardsJump));
    }

    @Test
    public void testKingCanMoveBothDirections() {
        final int startingPosition = 23;
        final Board testBoard = new Board(new ArrayList<>(), Arrays.asList(startingPosition));
        testBoard.getPiece(startingPosition).kingMe();

        Move forwardMove = new Move(startingPosition, 26, testBoard);
        assertTrue(MoveValidator.isValidMove(forwardMove));

        forwardMove = new Move(startingPosition, 27, testBoard);
        assertTrue(MoveValidator.isValidMove(forwardMove));

        Move backwardsMove = new Move(startingPosition, 18, testBoard);
        assertTrue(MoveValidator.isValidMove(backwardsMove));

        backwardsMove = new Move(startingPosition, 19, testBoard);
        assertTrue(MoveValidator.isValidMove(backwardsMove));
    }

    @Test
    public void testMoveIntoOccupiedSquare() {
        final int startingPosition = 23;
        final Board testBoard = new Board(Arrays.asList(19), Arrays.asList(startingPosition, 18));

        Move invalidMove = new Move(startingPosition, 18, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidMove));

        invalidMove = new Move(startingPosition, 19, testBoard);
        assertFalse(MoveValidator.isValidMove(invalidMove));
    }

    @Test
    public void testMoveOffLeftEdgeOfBoard() {
        final Board testBoard = new Board(Arrays.asList(21), Arrays.asList(13));
        Move badMove = new Move(13, 8, testBoard);
        assertFalse(MoveValidator.isValidMove(badMove));

        badMove = new Move(21, 24, testBoard);
        assertFalse(MoveValidator.isValidMove(badMove));
    }

    @Test
    public void testMoveOffRightEdgeOfBoard() {
        final Board testBoard = new Board(Arrays.asList(20), Arrays.asList(28));
        Move badMove = new Move(28, 25, testBoard);
        assertFalse(MoveValidator.isValidMove(badMove));

        badMove = new Move(20, 25, testBoard);
        assertFalse(MoveValidator.isValidMove(badMove));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerifyEndPositionsIsOnBoard() throws Exception {
        MoveValidator.verifyStartAndEndPositionsAreOnBoard(20, 33);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerifyMovePositionsAreNotEqual() throws Exception {
        MoveValidator.verifyStartAndEndPositionAreNotTheSame(20, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerifyStartPositionIsOnBoard() throws Exception {
        MoveValidator.verifyStartAndEndPositionsAreOnBoard(-1, 20);
    }
}
