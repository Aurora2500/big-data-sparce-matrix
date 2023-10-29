package es.ulpgc.bigdata.matrices.sparse.matrix;

public interface Matrix {
	double get(int row, int col);
	void set(int row, int col, double value);

	int rows();
	int cols();
}
