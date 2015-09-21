

public class Move implements MoveInterface {
	private int startingPosition;
	private int endingPosition;
	
	public Move(int startingPosition, int endingPosition) {
		this.startingPosition = startingPosition;
		this.endingPosition = endingPosition;
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
		return String.format("%d-%d", this.getStartingPosition(), this.getEndingPosition());
	}

}
