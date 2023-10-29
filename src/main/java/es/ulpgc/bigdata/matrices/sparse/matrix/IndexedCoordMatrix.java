package es.ulpgc.bigdata.matrices.sparse.matrix;

import java.util.*;

public class IndexedCoordMatrix extends CoordMatrix{
	HashMap<Integer, TreeMap<Integer, Double>> rowIndex = new HashMap<>(), colIndex = new HashMap<>();
	public IndexedCoordMatrix(int rows, int cols) {
		super(rows, cols);
	}

	public IndexedCoordMatrix(CoordMatrix source) {
		super(source);
		for( Map.Entry<Pair<Integer, Integer>, Double> element: elements.entrySet()) {
			int row = element.getKey().left();
			int col = element.getKey().right();
			double val = element.getValue();
			rowIndex.computeIfAbsent(row, k -> new TreeMap<>()).put(col, val);
			colIndex.computeIfAbsent(col, k -> new TreeMap<>()).put(row, val);
		}
	}

	@Override
	public void set(int row, int col, double value) {
		elements.put(new Pair<>(row, col), value);
		rowIndex.computeIfAbsent(row, k -> new TreeMap<>()).put(col, value);
		colIndex.computeIfAbsent(col, k -> new TreeMap<>()).put(row, value);
	}

	public TreeMap<Integer, Double> getRow(int row) {
		return rowIndex.getOrDefault(row, new TreeMap<>());
	}

	public TreeMap<Integer, Double> getCol(int col) {
		return colIndex.getOrDefault(col, new TreeMap<>());
	}

	public IndexedCoordMatrix multiply(IndexedCoordMatrix other) throws IllegalArgumentException {
		if (cols != other.rows()) {
			throw new IllegalArgumentException("Incompatible matrix sizes");
		}
		IndexedCoordMatrix result = new IndexedCoordMatrix(rows, other.cols());

		// for all the indexed rows and columns (this skips empty rows and columns)
		for(Map.Entry<Integer, TreeMap<Integer, Double>> leftIndex: rowIndex.entrySet()) {
			for (Map.Entry<Integer, TreeMap<Integer, Double>> rightIndex: other.colIndex.entrySet()) {
				int row = leftIndex.getKey();
				int col = rightIndex.getKey();

				// only look at the intersection of the columns & rows of these rows and columns, as the rest will be zero when
				// multiplied
				Set<Integer> intersection = new HashSet<>(leftIndex.getValue().keySet());
				intersection.retainAll(rightIndex.getValue().keySet());
				if (intersection.size() > 0) {
					// inner product
					double value = 0.0;
					for (int k : intersection) {
						value += leftIndex.getValue().get(k) * rightIndex.getValue().get(k);
					}
					result.set(row, col, value);
				}
			}
		}
		return result;
	}
}
