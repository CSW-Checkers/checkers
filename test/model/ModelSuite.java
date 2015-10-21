package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import model.ai.evaluation.BackRowCountEvaluatorTest;
import model.ai.evaluation.BoardEvaluatorSummatorTest;
import model.ai.evaluation.GameOverEvaluatorTest;
import model.ai.evaluation.KingCountEvaluatorTest;
import model.ai.evaluation.PawnCountEvaluatorTest;
import model.ai.search.AlphaBetaSearchNodeTest;
import model.ai.search.AlphaBetaSearchTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PieceTest.class, SquareTest.class, MoveTest.class, PieceColorTest.class,
        SingleJumpTest.class, MultiJumpTest.class, NullPieceTest.class, BoardTest.class,
        MoveValidatorTest.class, MoveGeneratorTest.class, BoardEvaluatorSummatorTest.class,
        BackRowCountEvaluatorTest.class, GameOverEvaluatorTest.class, KingCountEvaluatorTest.class,
        PawnCountEvaluatorTest.class, AlphaBetaSearchNodeTest.class, AlphaBetaSearchTest.class })

public class ModelSuite {
}
