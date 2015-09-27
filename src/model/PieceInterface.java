package model;

public interface PieceInterface {
    public PieceColor getColor();

    public boolean isBlack();

    public boolean isKing();

    public boolean isNull();

    public boolean isSameColorAs(PieceInterface otherPiece);

    public boolean isWhite();

    public void kingMe();
}
