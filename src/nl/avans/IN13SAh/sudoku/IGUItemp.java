package nl.avans.IN13SAh.sudoku;

public interface IGUItemp {
	public int getCurrentValue(int x, int y);

	public void setCurrentValue(int x, int y, int currentValue);

	public int getSolutionValue(int x, int y);

	public int[][] getSubRegion(int subregion);

	public int[] getColumn(int column);

	public int[] getRow(int row);

	public int getNumberOfColumns();

	public int getNumberOfRows();

	public int getSize();

	public EDifficulty getDifficulty();

	public void solveGame();

	public void generateGame();
}
