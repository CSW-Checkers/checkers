package model.ai.evaluation;

import java.util.HashMap;
import java.util.HashSet;

import model.Board;
import model.PieceColor;
import model.PieceInterface;

public class BoardPositionEvaluator implements BoardEvaluatorInterface {
    private static BoardEvaluatorInterface instance = null;

    public static BoardEvaluatorInterface getInstance() {
        if (instance == null) {
            instance = new BoardPositionEvaluator();
        }
        return instance;
    }

    private HashMap<Integer, Double> kingPositionValueMap;
    private HashMap<Integer, Double> whitePositionValueMap;
    private HashMap<Integer, Double> blackPositionValueMap;
    // private HashMap<Integer, Integer> basicTrapPositionMap;

    private BoardPositionEvaluator() {
        this.initializeKingPositionValueMap();
        this.initializeBlackPositionValueMap();
        this.initializeWhitePositionValueMap();
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
                blackValue += this.kingPositionValueMap.get(position);
            } else {
                blackValue += this.blackPositionValueMap.get(position);
            }
        }

        HashSet<Integer> whitePositions = positionColorMap.get(PieceColor.WHITE);
        double whiteValue = 0.0;
        for (Integer position : whitePositions) {
            PieceInterface piece = theBoard.getPiece(position);

            if (piece.isKing()) {
                whiteValue += this.kingPositionValueMap.get(position);
            } else {
                whiteValue += this.whitePositionValueMap.get(position);
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

    private void initializeBlackPositionValueMap() {
        this.blackPositionValueMap = new HashMap<>();
        this.blackPositionValueMap.put(1, 4.0);
        this.blackPositionValueMap.put(2, 4.0);
        this.blackPositionValueMap.put(3, 4.0);
        this.blackPositionValueMap.put(4, 4.0);
        this.blackPositionValueMap.put(5, 1.0);
        this.blackPositionValueMap.put(6, 0.0);
        this.blackPositionValueMap.put(7, 0.0);
        this.blackPositionValueMap.put(8, 0.0);
        this.blackPositionValueMap.put(9, 1.0);
        this.blackPositionValueMap.put(10, 1.0);
        this.blackPositionValueMap.put(11, 1.0);
        this.blackPositionValueMap.put(12, 2.0);
        this.blackPositionValueMap.put(13, 3.0);
        this.blackPositionValueMap.put(14, 2.0);
        this.blackPositionValueMap.put(15, 2.0);
        this.blackPositionValueMap.put(16, 2.0);
        this.blackPositionValueMap.put(17, 3.0);
        this.blackPositionValueMap.put(18, 3.0);
        this.blackPositionValueMap.put(19, 3.0);
        this.blackPositionValueMap.put(20, 4.0);
        this.blackPositionValueMap.put(21, 5.0);
        this.blackPositionValueMap.put(22, 4.0);
        this.blackPositionValueMap.put(23, 4.0);
        this.blackPositionValueMap.put(24, 4.0);
        this.blackPositionValueMap.put(25, 5.0);
        this.blackPositionValueMap.put(26, 5.0);
        this.blackPositionValueMap.put(27, 5.0);
        this.blackPositionValueMap.put(28, 6.0);
        this.blackPositionValueMap.put(29, 7.0);
        this.blackPositionValueMap.put(30, 7.0);
        this.blackPositionValueMap.put(31, 7.0);
        this.blackPositionValueMap.put(32, 7.0);
    }

    private void initializeKingPositionValueMap() {
        this.kingPositionValueMap = new HashMap<>();
        this.kingPositionValueMap.put(1, 5.0);
        this.kingPositionValueMap.put(2, 4.0);
        this.kingPositionValueMap.put(3, 3.0);
        this.kingPositionValueMap.put(4, -1.0);
        this.kingPositionValueMap.put(5, 5.0);
        this.kingPositionValueMap.put(6, 2.0);
        this.kingPositionValueMap.put(7, 2.0);
        this.kingPositionValueMap.put(8, 2.0);
        this.kingPositionValueMap.put(9, 2.0);
        this.kingPositionValueMap.put(10, 1.0);
        this.kingPositionValueMap.put(11, 1.0);
        this.kingPositionValueMap.put(12, 3.0);
        this.kingPositionValueMap.put(13, 4.0);
        this.kingPositionValueMap.put(14, 1.0);
        this.kingPositionValueMap.put(15, 0.0);
        this.kingPositionValueMap.put(16, 2.0);
        this.kingPositionValueMap.put(17, 2.0);
        this.kingPositionValueMap.put(18, 0.0);
        this.kingPositionValueMap.put(19, 1.0);
        this.kingPositionValueMap.put(20, 4.0);
        this.kingPositionValueMap.put(21, 3.0);
        this.kingPositionValueMap.put(22, 1.0);
        this.kingPositionValueMap.put(23, 1.0);
        this.kingPositionValueMap.put(24, 2.0);
        this.kingPositionValueMap.put(25, 2.0);
        this.kingPositionValueMap.put(26, 2.0);
        this.kingPositionValueMap.put(27, 2.0);
        this.kingPositionValueMap.put(28, 5.0);
        this.kingPositionValueMap.put(29, -1.0);
        this.kingPositionValueMap.put(30, 3.0);
        this.kingPositionValueMap.put(31, 4.0);
        this.kingPositionValueMap.put(32, 5.0);
    }

    // private void initializePawnTrapMap() {
    // this.basicTrapPositionMap = new HashMap<>();
    // this.basicTrapPositionMap.put(1, 10);
    // this.basicTrapPositionMap.put(28, 19);
    // }

    private void initializeWhitePositionValueMap() {
        this.whitePositionValueMap = new HashMap<>();
        this.whitePositionValueMap.put(1, 7.0);
        this.whitePositionValueMap.put(2, 7.0);
        this.whitePositionValueMap.put(3, 7.0);
        this.whitePositionValueMap.put(4, 7.0);
        this.whitePositionValueMap.put(5, 6.0);
        this.whitePositionValueMap.put(6, 5.0);
        this.whitePositionValueMap.put(7, 5.0);
        this.whitePositionValueMap.put(8, 5.0);
        this.whitePositionValueMap.put(9, 4.0);
        this.whitePositionValueMap.put(10, 4.0);
        this.whitePositionValueMap.put(11, 4.0);
        this.whitePositionValueMap.put(12, 5.0);
        this.whitePositionValueMap.put(13, 4.0);
        this.whitePositionValueMap.put(14, 3.0);
        this.whitePositionValueMap.put(15, 3.0);
        this.whitePositionValueMap.put(16, 3.0);
        this.whitePositionValueMap.put(17, 2.0);
        this.whitePositionValueMap.put(18, 2.0);
        this.whitePositionValueMap.put(19, 2.0);
        this.whitePositionValueMap.put(20, 3.0);
        this.whitePositionValueMap.put(21, 2.0);
        this.whitePositionValueMap.put(22, 1.0);
        this.whitePositionValueMap.put(23, 1.0);
        this.whitePositionValueMap.put(24, 1.0);
        this.whitePositionValueMap.put(25, 0.0);
        this.whitePositionValueMap.put(26, 0.0);
        this.whitePositionValueMap.put(27, 0.0);
        this.whitePositionValueMap.put(28, 1.0);
        this.whitePositionValueMap.put(29, 4.0);
        this.whitePositionValueMap.put(30, 4.0);
        this.whitePositionValueMap.put(31, 4.0);
        this.whitePositionValueMap.put(32, 4.0);
    }

}
