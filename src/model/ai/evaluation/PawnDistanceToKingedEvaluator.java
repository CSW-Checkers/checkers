package model.ai.evaluation;

import java.util.HashMap;
import java.util.HashSet;

import model.Board;
import model.PieceColor;
import model.PieceInterface;

public class PawnDistanceToKingedEvaluator implements BoardEvaluatorInterface {

    private static BoardEvaluatorInterface instance = null;

    public static BoardEvaluatorInterface getInstance() {
        if (instance == null) {
            instance = new PawnDistanceToKingedEvaluator();
        }
        return instance;
    }

    private PawnDistanceToKingedEvaluator() {
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double score = 0.0;

        HashMap<PieceColor, HashSet<Integer>> positionColorMap = theBoard.getColorPositionMap();

        HashSet<Integer> blackPositions = positionColorMap.get(PieceColor.BLACK);
        double blackValue = 0.0;
        for (Integer position : blackPositions) {
            PieceInterface piece = theBoard.getPiece(position);

            if (!piece.isKing()) {
                blackValue += theBoard.getSquare(position).getRowNumber();
            }
        }

        HashSet<Integer> whitePositions = positionColorMap.get(PieceColor.WHITE);
        double whiteValue = 0.0;
        for (Integer position : whitePositions) {
            PieceInterface piece = theBoard.getPiece(position);

            if (!piece.isKing()) {
                whiteValue += 9 - theBoard.getSquare(position).getRowNumber();
            }
        }

        if (color == PieceColor.BLACK) {
            score += blackValue;
            score -= whiteValue;
        } else {
            score += whiteValue;
            score -= blackValue;
        }

        return score;
    }

}
