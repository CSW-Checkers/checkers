package model;

public class Move implements MoveInterface {
    private Board board;
    private int endingPosition;
    private Piece piece;
    private int startingPosition;

    public Move(int startingPosition, int endingPosition, Board board) {
        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
        this.board = board;
        this.piece = board.getPiece(startingPosition);
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
    public Piece getPiece() {
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
        return String.format("%d-%d", this.getStartingPosition(), this.getEndingPosition());
    }

}
