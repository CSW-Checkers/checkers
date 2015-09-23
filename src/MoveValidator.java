import java.util.ArrayList;

//TODO Remove sysouts
//TODO Write method for MultiJump

public class MoveValidator {
    private static boolean isLegalMoveDirection(MoveInterface move) {
        boolean validMove = true;
        Piece piece = move.getPiece();
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

    public static boolean isValidMove(MoveInterface move) {
        System.out.println("MoveInterface isValidMove()");
        if (move.getStartingPosition() > 32 || move.getStartingPosition() < 1
                || move.getEndingPosition() > 32 || move.getEndingPosition() < 1) {
            // Illegal position
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
        System.out.println("MultiJump isValidMove()");
        for (SingleJump subJump : multijump.getSubJumps()) {
            if (!isValidSingleJump(subJump)) {
                return false;
            }

            // TODO Not finished
        }
    }

    private static boolean isValidNonJumpMove(Move move) {
        System.out.println("Move isValidMove()");
        Board board = move.getBoard();

        if (!isLegalMoveDirection(move)) {
            return false;
        }

        Square startingSquare = board.getSquare(move.getStartingPosition());
        Square endingSquare = board.getSquare(move.getEndingPosition());

        if (!startingSquare.getAdjacentSquares().contains(endingSquare)) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean isValidSingleJump(SingleJump jump) {
        System.out.println("SingleJump isValidMove()");
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
        Piece jumpedPiece = jump.getJumpedPieces().get(0);

        if (!jumpedSquare.isOccupied() || jump.getPiece().isSameColorAs(jumpedPiece)) {
            return false;
        } else if (positionDifference != 7 || positionDifference != 9) {
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

    public static void main(String[] args) {
        ArrayList<SingleJump> jumpList = new ArrayList<SingleJump>();
        jumpList.add(new SingleJump(10, 19, new Board()));
        jumpList.add(new SingleJump(19, 26, new Board()));
        boolean temp = MoveValidator.isValidMove(new MultiJump(10, 26, jumpList, new Board()));
        System.out.println(temp);
    }
}
