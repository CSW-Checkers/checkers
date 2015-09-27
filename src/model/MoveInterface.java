package model;

public interface MoveInterface {

    public Board getBoard();

    public int getEndingPosition();

    public Square getEndingSquare();

    public PieceInterface getPiece();

    public int getStartingPosition();

    public Square getStartingSquare();

    @Override
    public String toString();
}
