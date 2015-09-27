package model;

public class Piece implements PieceInterface {
    private final PieceColor color;
    boolean king;

    public Piece(PieceColor color) {
        this.color = color;
        this.king = false;
    }

    @Override
    public PieceColor getColor() {
        return this.color;
    }

    @Override
    public boolean isBlack() {
        return this.getColor().equals(PieceColor.BLACK);
    }

    @Override
    public boolean isKing() {
        return this.king;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isSameColorAs(PieceInterface otherPiece) {
        return this.color.equals(otherPiece.getColor());
    }

    @Override
    public boolean isWhite() {
        return this.getColor().equals(PieceColor.WHITE);
    }

    @Override
    public void kingMe() {
        this.king = true;
    }
}
