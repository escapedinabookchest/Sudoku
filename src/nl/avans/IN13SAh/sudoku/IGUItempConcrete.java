package nl.avans.IN13SAh.sudoku;

public class IGUItempConcrete implements IGUItemp {
	boolean test = false;

	public int getCurrentValue(int x, int y) {
		return test ? 3 : 2;
	}

	public void setCurrentValue(int x, int y, int currentValue) {
		test = true;
	}

	public int getSolutionValue(int x, int y) {
		return 0;
	}

	public int[][] getSubRegion(int subregion) {
		return new int[0][0];
	}

	public int[] getColumn(int column) {
		return new int[0];
	}

	public int[] getRow(int row) {
		return new int[0];
	}

	public int getNumberOfColumns() {
		return 9;
	}

	public int getNumberOfRows() {
		return 9;
	}

	public int getSize() {
		return 0;
	}

	public EDifficulty getDifficulty() {
		return EDifficulty.LEVEL_1;
	}

	@Override
	public void solveGame() {
		// TODO Auto-generated method stub
	}

	@Override
	public void generateGame() {
		// TODO Auto-generated method stub

	}
}
