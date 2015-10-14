package model;

public interface Player {
    public PieceColor getColor();

    public void makeMove(Board currentBoard);
}
