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
                    "Multijump must have at least one intermediate position. It has "
                            + intermediatePositions.size());
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        MultiJump other = (MultiJump) obj;
        if (this.board == null) {
            if (other.board != null) {
                return false;
            }
        } else if (!this.board.equals(other.board)) {
            return false;
        }
        if (this.endingPosition != other.endingPosition) {
            return false;
        }
        if (this.jumpedPositions == null) {
            if (other.jumpedPositions != null) {
                return false;
            }
        } else if (!this.jumpedPositions.equals(other.jumpedPositions)) {
            return false;
        }
        if (this.piece == null) {
            if (other.piece != null) {
                return false;
            }
        } else if (!this.piece.equals(other.piece)) {
            return false;
        }
        if (this.startingPosition != other.startingPosition) {
            return false;
        }
        if (this.subJumps == null) {
            if (other.subJumps != null) {
                return false;
            }
        } else if (!this.subJumps.equals(other.subJumps)) {
            return false;
        }
        return true;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.board == null) ? 0 : this.board.hashCode());
        result = prime * result + this.endingPosition;
        result = prime * result
                + ((this.jumpedPositions == null) ? 0 : this.jumpedPositions.hashCode());
        result = prime * result + ((this.piece == null) ? 0 : this.piece.hashCode());
        result = prime * result + this.startingPosition;
        result = prime * result + ((this.subJumps == null) ? 0 : this.subJumps.hashCode());
        return result;
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

}
