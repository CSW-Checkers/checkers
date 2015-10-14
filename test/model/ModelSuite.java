package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import model.ai.evaluation.BoardEvaluatorTest;
import model.ai.search.AlphaBetaSearchNodeTest;
import model.ai.search.AlphaBetaSearchTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PieceTest.class, SquareTest.class, MoveTest.class, PieceColorTest.class,
        SingleJumpTest.class, MultiJumpTest.class, NullPieceTest.class, BoardTest.class,
        MoveValidatorTest.class, MoveGeneratorTest.class, BoardEvaluatorTest.class,
        AlphaBetaSearchNodeTest.class, AlphaBetaSearchTest.class })

public class ModelSuite {
}
