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

    private TradePieceEvaluator() {
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double friendlyMaterialValue = (theBoard.getKingCount(color) * 1.5)
                + (theBoard.getPawnCount(color) * 1.0);

        PieceColor foeColor = color.getOppositeColor();
        double foeMaterialValue = (theBoard.getKingCount(foeColor) * 1.5)
                + (theBoard.getPawnCount(foeColor) * 1.0);

        double score = 0.0;
        if (friendlyMaterialValue > foeMaterialValue) {
            // Do stuff when material advantage
            score += 1 / (theBoard.getTotalNumberOfBlackPieces()
                    + theBoard.getTotalNumberOfWhitePieces());
        } else {
            // Do stuff if anything when not material advantage
            score -= 1 / (theBoard.getTotalNumberOfBlackPieces()
                    + theBoard.getTotalNumberOfWhitePieces());
        }

        return score;
    }

}
