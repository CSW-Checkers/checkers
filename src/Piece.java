
public class Piece {
    private final PieceColor color;
    boolean king;

    public Piece(PieceColor color) {
        this.color = color;
        this.king = false;
    }

    public PieceColor getColor() {
        return this.color;
    }

    public boolean isBlack() {
        return this.getColor().equals(PieceColor.BLACK);
    }

    public boolean isKing() {
        return this.king;
    }

    public boolean isSameColorAs(Piece otherPiece) {
        return this.color.equals(otherPiece.getColor());
    }

    public boolean isWhite() {
        return this.getColor().equals(PieceColor.WHITE);
    }

    public void kingMe() {
        this.king = true;
    }
}
