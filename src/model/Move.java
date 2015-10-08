package model;

public class Move implements MoveInterface {
    private Board board;
    private int endingPosition;
    private PieceInterface piece;
    private int startingPosition;

    public Move(int startingPosition, int endingPosition, Board board) {
        MoveValidator.verifyStartAndEndPositionsAreOnBoard(startingPosition, endingPosition);
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
        return String.format("%d-%d", this.getStartingPosition(), this.getEndingPosition());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((board == null) ? 0 : board.hashCode());
        result = prime * result + endingPosition;
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
        Move other = (Move) obj;
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
