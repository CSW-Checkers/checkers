package model;

public interface Player {
    public PieceColor getColor();

    public MoveInterface makeMove(Board currentBoard);
}
