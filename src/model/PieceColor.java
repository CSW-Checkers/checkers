package model;

public enum PieceColor {
    WHITE, BLACK;

    private PieceColor opposite;
    
    static {
        WHITE.opposite = BLACK;
        BLACK.opposite = WHITE;
    }
    
    public PieceColor getOppositeColor() {
        return opposite;
    }
}
