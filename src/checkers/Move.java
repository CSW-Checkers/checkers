package checkers;

public class Move {
	private final int startingPosition;
	private final int endingPosition;

	public Move(int startingPosition, int endingPosition)
	{
		this.startingPosition = startingPosition;
		this.endingPosition = endingPosition;
	}
	
	public int getStartingPosition()
	{
		return this.startingPosition;
	}
	
	public int getEndingPosition()
	{
		return this.endingPosition;
	}
}
