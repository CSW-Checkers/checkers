package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public class TradePieceEvaluator implements BoardEvaluatorInterface {
    private static BoardEvaluatorInterface instance = null;

    public static BoardEvaluatorInterface getInstance() {
        if (instance == null) {
            instance = new TradePieceEvaluator();
        }
        return instance;
    }

    private final double KING_WEIGHT = 1.5;
    private final double PAWN_WEIGHT = 1.0;

    private TradePieceEvaluator() {
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double friendlyMaterialValue = (theBoard.getKingCount(color) * this.KING_WEIGHT)
                + (theBoard.getNumberOfPawns(color) * this.PAWN_WEIGHT);

        PieceColor foeColor = color.getOppositeColor();
        double foeMaterialValue = (theBoard.getKingCount(foeColor) * this.KING_WEIGHT)
                + (theBoard.getNumberOfPawns(foeColor) * this.PAWN_WEIGHT);

        double materialDifference = friendlyMaterialValue - foeMaterialValue;
        double totalMaterial = friendlyMaterialValue + foeMaterialValue;

        return materialDifference / totalMaterial;
    }

}
