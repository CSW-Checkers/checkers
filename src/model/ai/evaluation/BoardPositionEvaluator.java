package model.ai.evaluation;

import java.util.HashMap;
import java.util.HashSet;

import model.Board;
import model.PieceColor;
import model.PieceInterface;

public class BoardPositionEvaluator implements BoardEvaluatorInterface {
    private static BoardEvaluatorInterface instance = null;
    private static HashMap<Integer, Double> kingPositionValueMap;
    private static HashMap<Integer, Double> whitePositionValueMap;
    private static HashMap<Integer, Double> blackPositionValueMap;

    public static BoardEvaluatorInterface getInstance() {
        if (instance == null) {
            instance = new BoardPositionEvaluator();
        }
        return instance;
    }

    private BoardPositionEvaluator() {
        initializeKingPositionValueMap();
        initializeBlackPositionValueMap();
        initializeWhitePositionValueMap();
    }

    private void initializeKingPositionValueMap() {
        kingPositionValueMap = new HashMap<>();
        kingPositionValueMap.put(1, 0.0);
        kingPositionValueMap.put(2, 0.0);
        kingPositionValueMap.put(3, 0.0);
        kingPositionValueMap.put(4, 0.0);
        kingPositionValueMap.put(5, 0.0);
        kingPositionValueMap.put(6, 1.0);
        kingPositionValueMap.put(7, 1.0);
        kingPositionValueMap.put(8, 1.0);
        kingPositionValueMap.put(9, 1.0);
        kingPositionValueMap.put(10, 2.0);
        kingPositionValueMap.put(11, 2.0);
        kingPositionValueMap.put(12, 0.0);
        kingPositionValueMap.put(13, 0.0);
        kingPositionValueMap.put(14, 2.0);
        kingPositionValueMap.put(15, 4.0);
        kingPositionValueMap.put(16, 1.0);
        kingPositionValueMap.put(17, 1.0);
        kingPositionValueMap.put(18, 4.0);
        kingPositionValueMap.put(19, 2.0);
        kingPositionValueMap.put(20, 0.0);
        kingPositionValueMap.put(21, 0.0);
        kingPositionValueMap.put(22, 2.0);
        kingPositionValueMap.put(23, 2.0);
        kingPositionValueMap.put(24, 1.0);
        kingPositionValueMap.put(25, 1.0);
        kingPositionValueMap.put(26, 1.0);
        kingPositionValueMap.put(27, 1.0);
        kingPositionValueMap.put(28, 0.0);
        kingPositionValueMap.put(29, 0.0);
        kingPositionValueMap.put(30, 0.0);
        kingPositionValueMap.put(31, 0.0);
        kingPositionValueMap.put(32, 0.0);
    }

    private void initializeBlackPositionValueMap() {
        blackPositionValueMap = new HashMap<>();
        blackPositionValueMap.put(1, 3.0);
        blackPositionValueMap.put(2, 3.0);
        blackPositionValueMap.put(3, 3.0);
        blackPositionValueMap.put(4, 3.0);
        blackPositionValueMap.put(5, 1.0);
        blackPositionValueMap.put(6, 0.0);
        blackPositionValueMap.put(7, 0.0);
        blackPositionValueMap.put(8, 0.0);
        blackPositionValueMap.put(9, 1.0);
        blackPositionValueMap.put(10, 1.0);
        blackPositionValueMap.put(11, 1.0);
        blackPositionValueMap.put(12, 2.0);
        blackPositionValueMap.put(13, 3.0);
        blackPositionValueMap.put(14, 2.0);
        blackPositionValueMap.put(15, 2.0);
        blackPositionValueMap.put(16, 2.0);
        blackPositionValueMap.put(17, 3.0);
        blackPositionValueMap.put(18, 3.0);
        blackPositionValueMap.put(19, 3.0);
        blackPositionValueMap.put(20, 4.0);
        blackPositionValueMap.put(21, 5.0);
        blackPositionValueMap.put(22, 4.0);
        blackPositionValueMap.put(23, 4.0);
        blackPositionValueMap.put(24, 4.0);
        blackPositionValueMap.put(25, 5.0);
        blackPositionValueMap.put(26, 5.0);
        blackPositionValueMap.put(27, 5.0);
        blackPositionValueMap.put(28, 6.0);
        blackPositionValueMap.put(29, 0.0);
        blackPositionValueMap.put(30, 0.0);
        blackPositionValueMap.put(31, 0.0);
        blackPositionValueMap.put(32, 0.0);
    }

    private void initializeWhitePositionValueMap() {
        whitePositionValueMap = new HashMap<>();
        whitePositionValueMap.put(1, 0.0);
        whitePositionValueMap.put(2, 0.0);
        whitePositionValueMap.put(3, 0.0);
        whitePositionValueMap.put(4, 0.0);
        whitePositionValueMap.put(5, 6.0);
        whitePositionValueMap.put(6, 5.0);
        whitePositionValueMap.put(7, 5.0);
        whitePositionValueMap.put(8, 5.0);
        whitePositionValueMap.put(9, 4.0);
        whitePositionValueMap.put(10, 4.0);
        whitePositionValueMap.put(11, 4.0);
        whitePositionValueMap.put(12, 5.0);
        whitePositionValueMap.put(13, 4.0);
        whitePositionValueMap.put(14, 3.0);
        whitePositionValueMap.put(15, 3.0);
        whitePositionValueMap.put(16, 3.0);
        whitePositionValueMap.put(17, 2.0);
        whitePositionValueMap.put(18, 2.0);
        whitePositionValueMap.put(19, 2.0);
        whitePositionValueMap.put(20, 3.0);
        whitePositionValueMap.put(21, 2.0);
        whitePositionValueMap.put(22, 1.0);
        whitePositionValueMap.put(23, 1.0);
        whitePositionValueMap.put(24, 1.0);
        whitePositionValueMap.put(25, 0.0);
        whitePositionValueMap.put(26, 0.0);
        whitePositionValueMap.put(27, 0.0);
        whitePositionValueMap.put(28, 1.0);
        whitePositionValueMap.put(29, 3.0);
        whitePositionValueMap.put(30, 3.0);
        whitePositionValueMap.put(31, 3.0);
        whitePositionValueMap.put(32, 3.0);
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double score = 0.0;

        HashMap<PieceColor, HashSet<Integer>> positionColorMap = theBoard.getColorPositionMap();

        HashSet<Integer> blackPositions = positionColorMap.get(PieceColor.BLACK);
        double blackValue = 0.0;
        for (Integer position : blackPositions) {
            PieceInterface piece = theBoard.getPiece(position);

            if (piece.isKing()) {
                blackValue += kingPositionValueMap.get(position);
            } else {
                blackValue += blackPositionValueMap.get(position);
            }
        }

        HashSet<Integer> whitePositions = positionColorMap.get(PieceColor.WHITE);
        double whiteValue = 0.0;
        for (Integer position : whitePositions) {
            PieceInterface piece = theBoard.getPiece(position);

            if (piece.isKing()) {
                whiteValue += kingPositionValueMap.get(position);
            } else {
                whiteValue += whitePositionValueMap.get(position);
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
