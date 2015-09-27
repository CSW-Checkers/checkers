package model;

public final class NullPiece implements PieceInterface {

    private static NullPiece instance = null;

    public static NullPiece getInstance() {
        if (instance == null) {
            instance = new NullPiece();
        }

        return instance;
    }

    private NullPiece() {
    }

    @Override
    public PieceColor getColor() {
        return null;
    }

    @Override
    public boolean isBlack() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public boolean isSameColorAs(PieceInterface otherPiece) {
        return false;
    }

    @Override
    public boolean isWhite() {
        return false;
    }

    @Override
    public void kingMe() {
    }

}
