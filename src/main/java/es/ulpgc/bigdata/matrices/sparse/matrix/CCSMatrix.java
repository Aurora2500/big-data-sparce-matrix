package es.ulpgc.bigdata.matrices.sparse.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CCSMatrix {
	private ArrayList<Integer> col_ptr = new ArrayList<>();
	private ArrayList<Integer> row = new ArrayList<>();
	private ArrayList<Double> value = new ArrayList<>();

	private final int rows;

	public CCSMatrix(CoordMatrix source) {
		col_ptr.ensureCapacity(source.cols() + 1);
		row.ensureCapacity(source.elements.size());
		rows = source.rows();
		value.ensureCapacity(source.elements.size());

		col_ptr.add(0);

		List<Map.Entry<Pair<Integer, Integer>, Double>> elements = source.getElements().stream().sorted((a, b) -> {
			int rowComp = a.getKey().left().compareTo(b.getKey().left());
			int colComp = a.getKey().right().compareTo(b.getKey().right());
			return colComp != 0 ? colComp : rowComp;
		}).toList();

		int currentCol = 0;
		int currentElement = 0;

		for (Map.Entry<Pair<Integer, Integer>, Double> element : elements) {
			int col = element.getKey().right();
			int row = element.getKey().left();
			double val = element.getValue();

			while (currentCol < col) {
				col_ptr.add(currentElement);
				currentCol++;
			}

			this.row.add(row);
			value.add(val);
			currentElement++;
		}
		col_ptr.add(currentElement);
	}

	public Double get(int col, int row) {
		int start = col_ptr.get(col);
		int end = col_ptr.get(col + 1);
		for (int i = start; i < end; i++) {
			if (this.row.get(i) == row) {
				return value.get(i);
			}
		}
		return 0.0;
	}

	public int rows() {
		return rows;
	}

	public int cols() {
		return col_ptr.size() - 1;
	}

	public CCSMatrix multiply(CCSMatrix other) throws IllegalArgumentException {
		if (this.cols() != other.rows()) {
			throw new IllegalArgumentException("Incompatible matrix sizes");
		}
		Set<Integer> leftRows = this.row.stream().distinct().collect(Collectors.toSet());
		CoordMatrix result = new CoordMatrix(this.rows(), other.cols());
		for (int col = 0; col < other.cols(); col++) {
			int elements = other.col_ptr.get(col + 1) - other.col_ptr.get(col);
			if (elements == 0) {
				continue;
			}
			for (int row : leftRows) {
				boolean hasValue = false;
				double sum = 0;

				int start = other.col_ptr.get(col);
				int end = other.col_ptr.get(col + 1);

				for (int iRight = start; iRight < end; iRight++) {
					int rightRow = other.row.get(iRight);

					int iLeft = this.col_ptr.get(rightRow);
					int leftEnd = this.col_ptr.get(rightRow + 1);

					while ( iLeft < (leftEnd - 1) && this.row.get(iLeft) < row) {
						iLeft++;
					}
					if (this.row.get(iLeft) == iLeft) {
						hasValue = true;
						sum += this.value.get(iLeft) * other.value.get(iRight);
					}
				}
				if (hasValue) {
					result.set(row, col, sum);
				}
			}
		}
		return new CCSMatrix(result);
	}

	public boolean epsilonEquals(CCSMatrix other, double epsilon) {
		for (int col = 0; col < cols(); col++) {
			int start = col_ptr.get(col);
			int end = col_ptr.get(col + 1);
			if (end - start != other.col_ptr.get(col + 1) - other.col_ptr.get(col)) {
				return false;
			}
			for (int i = start; i < end; i++) {
				int row = this.row.get(i);
				double val = this.value.get(i);
				if (Math.abs(val - other.get(col, row)) > epsilon) {
					return false;
				}
			}
		}
		return true;
	}
}
