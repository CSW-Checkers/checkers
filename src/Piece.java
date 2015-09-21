

public class Piece {
	private final PieceColor color;
	boolean king;
	
	public Piece(PieceColor color)
	{
		this.color = color;
		this.king = false;
	}
	
	public void kingMe()
	{
		this.king = true;
	}
	
	public boolean isKing()
	{
		return this.king;
	}
	
	public PieceColor getColor()
	{
		return this.color;
	}
}
