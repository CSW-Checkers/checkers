package checkers;

import java.awt.Color;

public class Piece {
	private final Color color;
	boolean king;
	
	public Piece(Color color)
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
	
	public Color getColor()
	{
		return this.color;
	}
}
