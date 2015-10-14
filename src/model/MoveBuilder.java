package model;

import java.util.ArrayList;
import java.util.List;

public final class MoveBuilder {
    public static MoveInterface buildMove(String moveString, Board board) {
        final MoveInterface moveToReturn;
        if (moveString.contains("-")) {
            final int startPosition = Integer.parseInt(moveString.split("-")[0]);
            final int endPosition = Integer.parseInt(moveString.split("-")[1]);
            moveToReturn = new Move(startPosition, endPosition, board);
        } else {
            final ArrayList<Integer> positions = new ArrayList<Integer>();
            for (final String positionString : moveString.split("x")) {
                positions.add(Integer.parseInt(positionString));
            }
            final int positionsSize = positions.size();
            final int startingPosition = positions.get(0);
            final int endingPosition = positions.get(positionsSize - 1);

            if (positionsSize > 2) {
                final List<Integer> intermediatePositions = positions.subList(1, positionsSize - 1);
                moveToReturn = new MultiJump(startingPosition, endingPosition,
                        intermediatePositions, board);
            } else {
                moveToReturn = new SingleJump(startingPosition, endingPosition, board);
            }
        }

        return moveToReturn;
    }

    private MoveBuilder() {
    }
}
