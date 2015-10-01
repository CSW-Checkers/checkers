package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PieceTest.class, SquareTest.class, MoveTest.class, PieceColorTest.class,
        SingleJumpTest.class, MultiJumpTest.class, NullPieceTest.class, BoardTest.class,
        MoveValidatorTest.class })

public class ModelSuite {
}
