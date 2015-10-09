package model;

import java.util.ArrayList;
import java.util.List;

public class MultiJump implements Jump {
    private Board board;
    private int endingPosition;
    private ArrayList<Integer> jumpedPositions;
    private PieceInterface piece;
    private int startingPosition;
    private ArrayList<SingleJump> subJumps;

    public MultiJump(int startingPosition, int endingPosition, List<Integer> intermediatePositions,
            Board board) {
        MoveValidator.verifyStartAndEndPositionsAreOnBoard(startingPosition, endingPosition);
        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
        this.subJumps = this.constructSubJumpsFromPositions(startingPosition, endingPosition,
                intermediatePositions, board);
        this.jumpedPositions = this.determineJumpedPositions();
        this.piece = board.getPiece(startingPosition);
        this.board = board;
    }

    private ArrayList<SingleJump> constructSubJumpsFromPositions(int startingPosition,
            int endingPosition, List<Integer> intermediatePositions, Board board) {
        if (intermediatePositions.size() < 1) {
            throw new IllegalArgumentException(
                    "Multijump must have at least one intermediate position");
        }
        Board tempBoard = new Board(board);
        ArrayList<SingleJump> subJumps = new ArrayList<SingleJump>();
        int lastEndPosition = startingPosition;
        SingleJump currentJump;

        for (int intermediatePosition : intermediatePositions) {
            currentJump = new SingleJump(lastEndPosition, intermediatePosition, tempBoard);
            subJumps.add(currentJump);
            tempBoard = new Board(tempBoard);
            tempBoard.movePiece(currentJump);
            lastEndPosition = intermediatePosition;
        }

        currentJump = new SingleJump(lastEndPosition, endingPosition, tempBoard);
        subJumps.add(currentJump);

        return subJumps;
    }

    private ArrayList<Integer> determineJumpedPositions() {
        ArrayList<Integer> jumpedPositions = new ArrayList<>();

        for (SingleJump jump : this.subJumps) {
            jumpedPositions.addAll(jump.getJumpedPositions());
        }

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

    public ArrayList<SingleJump> getSubJumps() {
        return this.subJumps;
    }

    @Override
    public String toString() {
        String moveNotation = "";

        SingleJump lastJump = null;

        for (SingleJump jump : this.subJumps) {
            moveNotation += jump.getStartingPosition() + "x";
            lastJump = jump;
        }

        // Add ending position
        moveNotation += lastJump.getEndingPosition();

        return moveNotation;
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
        result = prime * result + ((subJumps == null) ? 0 : subJumps.hashCode());
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
        MultiJump other = (MultiJump) obj;
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
        if (subJumps == null) {
            if (other.subJumps != null) {
                return false;
            }
        } else if (!subJumps.equals(other.subJumps)) {
            return false;
        }
        return true;
    }

}
