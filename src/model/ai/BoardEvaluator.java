package model.ai;

import model.Board;
import model.PieceColor;

public interface BoardEvaluator {
    public double evaluateBoard(Board theBoard, PieceColor color);
}