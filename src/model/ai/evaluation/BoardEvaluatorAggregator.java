package model.ai.evaluation;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardEvaluatorAggregator implements BoardEvaluatorInterface {
    List<BoardEvaluatorInterface> boardEvalutors;

    public BoardEvaluatorAggregator() {
        this.boardEvalutors = new ArrayList<>();
    }

    public BoardEvaluatorAggregator(List<BoardEvaluatorInterface> boardEvaluators) {
        this.boardEvalutors = boardEvaluators;
    }

    public void addBoardEvaluator(BoardEvaluatorInterface boardEvaluator) {
        this.boardEvalutors.add(boardEvaluator);
    }

}
