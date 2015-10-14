package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import model.ai.AlphaBetaSearchNodeTest;
import model.ai.AlphaBetaSearchTest;
import model.ai.PieceCountEvaluatorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PieceTest.class, SquareTest.class, MoveTest.class, PieceColorTest.class,
        SingleJumpTest.class, MultiJumpTest.class, NullPieceTest.class, BoardTest.class,
        MoveValidatorTest.class, MoveGeneratorTest.class, PieceCountEvaluatorTest.class,
        AlphaBetaSearchNodeTest.class, AlphaBetaSearchTest.class })

public class ModelSuite {
}
