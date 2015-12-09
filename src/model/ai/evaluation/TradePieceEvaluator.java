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

        double score = 0.0;
        if (friendlyMaterialValue > foeMaterialValue) {
            // Do stuff when material advantage
            score += 100 / (theBoard.getTotalNumberOfPieces(foeColor) + 1);
        } else if (friendlyMaterialValue < foeMaterialValue) {
            // Do stuff if anything when not material advantage
            score -= 100 / (theBoard.getTotalNumberOfPieces(color) + 1);
        }

        return score;
    }

}
