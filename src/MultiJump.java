
import java.util.ArrayList;

public class MultiJump implements Jump {
    private int startingPosition;
    private int endingPosition;
    private ArrayList<SingleJump> subJumps;
    private ArrayList<Integer> jumpedPositions;

    public MultiJump(int startingPosition, int endingPosition, ArrayList<SingleJump> subJumps) {
        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
        this.subJumps = subJumps;
        this.jumpedPositions = determineJumpedPositions();
    }

    @Override
    public int getStartingPosition() {
        return this.startingPosition;
    }

    @Override
    public int getEndingPosition() {
        return this.endingPosition;
    }

    @Override
    public String toString() {
        String moveNotation = "";

        for (SingleJump jump : subJumps) {
            moveNotation += jump.getStartingPosition() + "x";
        }

        // Strip off trailing 'x'
        moveNotation = moveNotation.substring(0, moveNotation.length() - 1);

        return moveNotation;
    }

    private ArrayList<Integer> determineJumpedPositions() {
        ArrayList<Integer> jumpedPositions = new ArrayList<>();

        for (SingleJump jump : subJumps) {
            jumpedPositions.addAll(jump.getJumpedPositions());
        }

        return jumpedPositions;
    }

    public ArrayList<Integer> getJumpedPositions() {
        return this.getJumpedPositions();
    }
}
