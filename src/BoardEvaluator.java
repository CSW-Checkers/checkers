
public interface BoardEvaluator {
	public double evaluateBoard(Board theBoard);
}

class TestBoardEvaluator implements BoardEvaluator {

	@Override
	public double evaluateBoard(Board theBoard) {
		return 1;
	}
	
}