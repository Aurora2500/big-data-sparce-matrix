package es.ulpgc.bigdata.matrices.sparse.matrix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CoordMatrix implements Matrix {

	protected HashMap<Pair<Integer, Integer>, Double> elements = new HashMap<>();
	protected final int rows;
	protected final int cols;

	public CoordMatrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	public CoordMatrix(CoordMatrix other) {
		this.rows = other.rows;
		this.cols = other.cols;

		this.elements = new HashMap<>(other.elements);
	}

	public static CoordMatrix fromParts(int rows, int cols, HashMap<Pair<Integer, Integer>, Double> elements) {
		CoordMatrix matrix = new CoordMatrix(rows, cols);
		matrix.elements = elements;
		return matrix;
	}

	private void assertBounds(int row, int col) throws IndexOutOfBoundsException {
		if (row < 0 || row >= rows) {
			throw new IndexOutOfBoundsException("Row index out of bounds: " + row);
		}
		if (col < 0 || col >= cols) {
			throw new IndexOutOfBoundsException("Column index out of bounds: " + col);
		}
	}

	public double get(int row, int col) throws IndexOutOfBoundsException {
		assertBounds(row, col);
		return elements.getOrDefault(new Pair<>(row, col), 0.0);
	};
	public boolean hasValue(int row, int col) {
		return elements.containsKey(new Pair<>(row, col));
	}

	public void set(int row, int col, double value) throws IndexOutOfBoundsException {
		assertBounds(row, col);
		elements.put(new Pair<>(row, col), value);
	}

	@Override
	public int rows() {
		return rows;
	}

	@Override
	public int cols() {
		return cols;
	}

	public CoordMatrix multiply(CoordMatrix other) {
		if (cols != other.rows()) {
			throw new IllegalArgumentException("Incompatible matrix sizes");
		}
		CoordMatrix result = new CoordMatrix(rows, other.cols());
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < other.cols(); j++) {
				boolean hasValue = false;
				double sum = 0;
				for (int k = 0; k < cols; k++) {
					if (hasValue(i, k) && other.hasValue(k, j)) {
						sum += get(i, k) * other.get(k, j);
						hasValue = true;
					}
				}
				if (hasValue) {
					result.set(i, j, sum);
				}
			}
		}
		return result;
	}

	public boolean epsilonEquals(CoordMatrix other, double epsilon) {

		if (rows != other.rows || cols != other.cols) {
			return false;
		}

		// check that element sets are equal
		HashSet<Pair<Integer, Integer>> keys1 = new HashSet<>(elements.keySet());
		HashSet<Pair<Integer, Integer>> keys2 = new HashSet<>(other.elements.keySet());
		if (!keys1.equals(keys2)) {
			return false;
		}

		// check that elements are equal
		for (Pair<Integer, Integer> key : keys1) {
			double v1 = elements.get(key);
			double v2 = other.elements.get(key);
			if (Math.abs(v1 - v2) > epsilon) {
				return false;
			}
		}
		return true;
	}

	Set<Map.Entry<Pair<Integer, Integer>, Double>> getElements() {
		return elements.entrySet();
	}
}
