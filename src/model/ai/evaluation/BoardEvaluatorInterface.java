package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public interface BoardEvaluatorInterface {
    public double evaluateBoard(Board theBoard, PieceColor color);
}
