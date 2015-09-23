
import java.util.ArrayList;

public class SingleJump implements Jump {
    private int startingPosition;
    private int endingPosition;
    private ArrayList<Integer> jumpedPositions;

    public SingleJump(int startingPosition, int endingPosition) {
        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
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
        return String.format("%dx%d", this.getStartingPosition(), this.getEndingPosition());
    }

    private ArrayList<Integer> determineJumpedPositions() {
        ArrayList<Integer> jumpedPositions = new ArrayList<>(1);
        int jumpedPosition = -1;

        if (Math.abs((this.startingPosition - this.endingPosition)) == 7) {
            jumpedPosition = Math.max(this.startingPosition, this.endingPosition) - 3;
        } else if (Math.abs((this.startingPosition - this.endingPosition)) == 9) {
            jumpedPosition = Math.max(this.startingPosition, this.endingPosition) - 4;
        } else {
            System.err.println("Invalid Single Jump!");
            System.out.println("SingleJump.determineJumpedPosition()");
            System.exit(1);
        }

        jumpedPositions.add(jumpedPosition);
        return jumpedPositions;
    }

    public ArrayList<Integer> getJumpedPositions() {
        return this.jumpedPositions;
    }

}
