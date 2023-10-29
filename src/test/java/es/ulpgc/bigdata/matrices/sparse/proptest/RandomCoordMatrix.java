package es.ulpgc.bigdata.matrices.sparse.proptest;

import es.ulpgc.bigdata.matrices.sparse.matrix.CoordMatrix;

public class RandomCoordMatrix {
	public static CoordMatrix newRandom(int rows, int cols, double density) {
		CoordMatrix matrix = new CoordMatrix(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (Math.random() < density) {
					matrix.set(i, j, Math.random());
				}
			}
		}
		return matrix;
	}

	public static CoordMatrix eye(int n) {
		CoordMatrix matrix = new CoordMatrix(n, n);
		for(int i = 0; i < n; i++) {
			matrix.set(i, i, 1.0);
		}
		return matrix;
	}
}
