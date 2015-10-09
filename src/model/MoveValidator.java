package model;

import java.util.ArrayList;
import java.util.List;

public class MoveValidator {
    private static boolean isLegalMoveDirection(MoveInterface move) {
        boolean validMove = true;
        PieceInterface piece = move.getPiece();
        if (!piece.isKing()) {
            if (piece.isBlack()) {
                if (move.getStartingPosition() > move.getEndingPosition()) {
                    validMove = false;
                }
            } else {
                if (move.getStartingPosition() < move.getEndingPosition()) {
                    validMove = false;
                }
            }
        }
        return validMove;
    }

    public static boolean isOnBoard(int position) {
        return position >= 1 && position <= 32;
    }

    public static boolean isValidMove(MoveInterface move) {
        if (move.getPiece().isNull()) {
            return false;
        } else if (move.getEndingSquare().isOccupied()) {
            return false;
        } else {
            if (move instanceof Move) {
                if (!isValidNonJumpMove((Move) move)) {
                    return false;
                }
            } else if (move instanceof SingleJump) {
                if (!isValidSingleJump((SingleJump) move)) {
                    return false;
                }
            } else if (move instanceof MultiJump) {
                if (!isValidMultiJump((MultiJump) move)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidMultiJump(MultiJump multijump) {
        ArrayList<SingleJump> subJumps = multijump.getSubJumps();

        // Check that each subJump is valid
        for (SingleJump subJump : subJumps) {
            if (!isValidSingleJump(subJump)) {
                return false;
            }
        }

        // Check that subJumps are linked legally
        int endingPosition = subJumps.get(0).getEndingPosition();
        int startingPosition;
        for (int i = 1; i < subJumps.size(); i++) {
            startingPosition = subJumps.get(i).getStartingPosition();

            if (startingPosition != endingPosition) {
                return false;
            } else {
                endingPosition = subJumps.get(i).getEndingPosition();
            }
        }
        return true;
    }

    private static boolean isValidNonJumpMove(Move move) {
        Board board = move.getBoard();

        if (!isLegalMoveDirection(move)) {
            return false;
        }

        List<Integer> startingSquareAdjacentPositions = board.getSquare(move.getStartingPosition())
                .getAdjacentPositions();
        int endingSquarePosition = board.getSquare(move.getEndingPosition()).getPosition();

        if (!startingSquareAdjacentPositions.contains(endingSquarePosition)) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean isValidSingleJump(SingleJump jump) {
        if (!isLegalMoveDirection(jump)) {
            return false;
        } else if (!isValidSingleJumpHelper(jump)) {
            return false;
        } else {
            return true;
        }

    }

    private static boolean isValidSingleJumpHelper(SingleJump jump) {
        Square jumpedSquare = jump.getJumpedSquares().get(0);
        int positionDifference = Math.abs(jump.getStartingPosition() - jump.getEndingPosition());
        PieceInterface jumpedPiece = jump.getJumpedPieces().get(0);

        if (!jumpedSquare.isOccupied()) {
            return false;
        } else if (jump.getPiece().isSameColorAs(jumpedPiece)) {
            return false;
        } else if (jump.getStartingSquare().isInLeftTwoColumns()) {
            // Handle jumping off the left edge of the board
            if (jump.getStartingPosition() > jump.getEndingPosition() && positionDifference != 7) {
                return false;
            } else if (jump.getStartingPosition() < jump.getEndingPosition()
                    && positionDifference != 9) {
                return false;
            }
        } else if (jump.getStartingSquare().isInRightTwoColumns()) {
            // Handle jumping off the right edge of the board
            if (jump.getStartingPosition() > jump.getEndingPosition() && positionDifference != 9) {
                return false;
            } else if (jump.getStartingPosition() < jump.getEndingPosition()
                    && positionDifference != 7) {
                return false;
            }
        }

        return true;
    }

    public static void verifyStartAndEndPositionsAreOnBoard(int startingPosition,
            int endingPosition) {
        if (!MoveValidator.isOnBoard(startingPosition)) {
            throw new IllegalArgumentException("Starting position is invalid: " + startingPosition);
        } else if (!MoveValidator.isOnBoard(endingPosition)) {
            throw new IllegalArgumentException("Ending position is invalid: " + startingPosition);
        }
    }

    public static void verifyStartAndEndPositionAreNotTheSame(int startingPosition,
            int endingPosition) {
        if (startingPosition == endingPosition) {
            throw new IllegalArgumentException(
                    "Starting position cannot be equal to the ending position.");
        }
    }
}
