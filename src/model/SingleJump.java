package model;

import java.util.ArrayList;

public class SingleJump implements Jump {

    public static ArrayList<SingleJump> singleJumpListCopier(ArrayList<SingleJump> otherList) {
        ArrayList<SingleJump> newList = new ArrayList<SingleJump>();
        for (SingleJump jump : otherList) {
            newList.add(new SingleJump(jump));
        }
        return newList;
    }

    private Board board;
    private int endingPosition;
    private ArrayList<Integer> jumpedPositions;

    private PieceInterface piece;

    private int startingPosition;

    public SingleJump(int startingPosition, int endingPosition, Board board) {
        MoveValidator.verifyStartAndEndPositionsAreOnBoard(startingPosition, endingPosition);
        MoveValidator.verifyStartAndEndPositionAreNotTheSame(startingPosition, endingPosition);
        int positionDifference = Math.abs(startingPosition - endingPosition);
        boolean invalidPositionDifference = !(positionDifference == 7 || positionDifference == 9);
        if (invalidPositionDifference) {
            throw new IllegalArgumentException(
                    "Position difference must be 7 or 9! Position difference is: "
                            + positionDifference);
        } else {
            this.startingPosition = startingPosition;
            this.endingPosition = endingPosition;
            this.board = board;
            this.piece = this.board.getPiece(startingPosition);
            this.jumpedPositions = this.determineJumpedPositions();
        }
    }

    public SingleJump(SingleJump otherJump) {
        this.board = new Board(otherJump.getBoard());
        this.endingPosition = otherJump.getEndingPosition();
        this.piece = new Piece(otherJump.getPiece());
        this.startingPosition = otherJump.getStartingPosition();
        this.jumpedPositions = otherJump.determineJumpedPositions();
    }

    private ArrayList<Integer> determineJumpedPositions() {
        ArrayList<Integer> jumpedPositions = new ArrayList<>(1);
        int jumpedPosition = -1;

        final int positionDifference = Math.abs((this.startingPosition - this.endingPosition));
        final int startingPositionRowNumber = this.board.getSquare(this.startingPosition)
                .getRowNumber();
        final boolean startingPositionIsOnEvenRow = startingPositionRowNumber % 2 == 0;

        if (positionDifference == 7) {
            if (startingPositionIsOnEvenRow) {
                jumpedPosition = Math.max(this.startingPosition, this.endingPosition) - 4;
            } else {
                jumpedPosition = Math.max(this.startingPosition, this.endingPosition) - 3;
            }
        } else if (positionDifference == 9) {
            if (startingPositionIsOnEvenRow) {
                jumpedPosition = Math.max(this.startingPosition, this.endingPosition) - 5;
            } else {
                jumpedPosition = Math.max(this.startingPosition, this.endingPosition) - 4;
            }
        }

        jumpedPositions.add(jumpedPosition);
        return jumpedPositions;
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    @Override
    public int getEndingPosition() {
        return this.endingPosition;
    }

    @Override
    public Square getEndingSquare() {
        return this.getBoard().getSquare(this.getEndingPosition());
    }

    @Override
    public ArrayList<PieceInterface> getJumpedPieces() {
        return this.board.getPieces(this.getJumpedPositions());
    }

    @Override
    public ArrayList<Integer> getJumpedPositions() {
        return this.jumpedPositions;
    }

    @Override
    public ArrayList<Square> getJumpedSquares() {
        return this.board.getSquares(this.getJumpedPositions());
    }

    @Override
    public PieceInterface getPiece() {
        return this.piece;
    }

    @Override
    public int getStartingPosition() {
        return this.startingPosition;
    }

    @Override
    public Square getStartingSquare() {
        return this.getBoard().getSquare(this.getStartingPosition());
    }

    @Override
    public String toString() {
        return String.format("%dx%d", this.getStartingPosition(), this.getEndingPosition());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((board == null) ? 0 : board.hashCode());
        result = prime * result + endingPosition;
        result = prime * result + ((jumpedPositions == null) ? 0 : jumpedPositions.hashCode());
        result = prime * result + ((piece == null) ? 0 : piece.hashCode());
        result = prime * result + startingPosition;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SingleJump other = (SingleJump) obj;
        if (board == null) {
            if (other.board != null) {
                return false;
            }
        } else if (!board.equals(other.board)) {
            return false;
        }
        if (endingPosition != other.endingPosition) {
            return false;
        }
        if (jumpedPositions == null) {
            if (other.jumpedPositions != null) {
                return false;
            }
        } else if (!jumpedPositions.equals(other.jumpedPositions)) {
            return false;
        }
        if (piece == null) {
            if (other.piece != null) {
                return false;
            }
        } else if (!piece.equals(other.piece)) {
            return false;
        }
        if (startingPosition != other.startingPosition) {
            return false;
        }
        return true;
    }
}
