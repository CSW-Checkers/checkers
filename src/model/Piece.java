package model;

public class Piece implements PieceInterface {
    private final PieceColor color;
    boolean king;

    public Piece(PieceColor color) {
        this.color = color;
        this.king = false;
    }

    public Piece(PieceColor color, boolean isKing) {
        this.color = color;
        this.king = isKing;
    }

    public Piece(PieceInterface otherPiece) {
        this.color = otherPiece.getColor();
        this.king = otherPiece.isKing();
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + (king ? 1231 : 1237);
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
        Piece other = (Piece) obj;
        if (color != other.color) {
            return false;
        }
        if (king != other.king) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Piece [color=" + color + ", king=" + king + "]";
    }
}
