package model;

import java.util.ArrayList;

public interface Jump extends MoveInterface {
    public ArrayList<Piece> getJumpedPieces();

    public ArrayList<Integer> getJumpedPositions();

    public ArrayList<Square> getJumpedSquares();
}
