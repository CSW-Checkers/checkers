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
        MoveValidator.verifyStartAndEndPositions(startingPosition, endingPosition);
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

}
