

import java.util.ArrayList;
import java.util.List;

public class Board {
	private List<Square> gameState;
	private int numberOfBlackPieces;
	private int numberOfWhitePieces;
	
	public Board()
	{
		this.gameState = getStartingGameBoardState();
		this.numberOfBlackPieces = 12;
		this.numberOfWhitePieces = 12;
	}

	private List<Square> getStartingGameBoardState() {
		List<Square> startingGameBoard = new ArrayList<>(32);
		
		for (int i = 0; i < 32; i++)
		{
			if (i < 12)
			{
				startingGameBoard.add(new Square(i, new Piece(PieceColor.BLACK)));
			}
			else if (i >= 12 && i < 21)
			{
				startingGameBoard.add(new Square(i, null));
			}
			else
			{
				startingGameBoard.add(new Square(i, new Piece(PieceColor.WHITE)));
			}
		}	
		
		return startingGameBoard;
	}
	
	public List<Square> getGameState() {
		return gameState;
	}
	
	public Square getSquare(int position)
	{
		return this.gameState.get(position - 1);
	}
	
	public Piece getPiece(int position)
	{
		return this.getSquare(position).getOccupyingPiece();
	}
	
	public void removePiece(int position)
	{
		this.updatePieceCount(position);
		this.getSquare(position).removeOccupyingPiece();
	}
	
	private void updatePieceCount(int position)
	{
		if (getPiece(position).getColor().equals(PieceColor.WHITE))
		{
			this.numberOfWhitePieces--;
		}
		else
		{
			this.numberOfBlackPieces--;
		}
	}
	
	private Piece pickUpPiece(int position)
	{
		Piece pieceToPickUp = this.getPiece(position);
		this.removePiece(position);
		return pieceToPickUp;
	}
	
	public void movePiece(MoveInterface move)
	{
		Piece pieceToMove = this.pickUpPiece(move.getStartingPosition());
		if (move instanceof Jump)
		{
			Jump jump = (Jump) move;
			
			for (int position : jump.getJumpedPositions())
			{
				removePiece(position);
			}
		}
		this.getSquare(move.getEndingPosition()).setOccupyingPiece(pieceToMove);
	}
	
	public int getNumberOfBlackPieces() {
		return numberOfBlackPieces;
	}
	
	public int getNumberOfWhitePieces() {
		return numberOfWhitePieces;
	}
}
