package model;

import java.util.ArrayList;

public class SingleJump implements Jump {
    private Board board;
    private int endingPosition;
    private ArrayList<Integer> jumpedPositions;
    private PieceInterface piece;
    private int startingPosition;

    public SingleJump(int startingPosition, int endingPosition, Board board) {
        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
        this.jumpedPositions = this.determineJumpedPositions();
        this.piece = board.getPiece(startingPosition);
        this.board = board;
    }

    private ArrayList<Integer> determineJumpedPositions() {
        ArrayList<Integer> jumpedPositions = new ArrayList<>(1);
        int jumpedPosition = -1;

        if (Math.abs((this.startingPosition - this.endingPosition)) == 7) {
            jumpedPosition = Math.max(this.startingPosition, this.endingPosition) - 3;
        } else if (Math.abs((this.startingPosition - this.endingPosition)) == 9) {
            jumpedPosition = Math.max(this.startingPosition, this.endingPosition) - 4;
        } else {
            System.err.println("Invalid Single Jump!");
            System.out.println("SingleJump.determineJumpedPosition()");
            System.exit(1);
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

}
