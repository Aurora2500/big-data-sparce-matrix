package org.example;

import java.util.HashMap;
import java.util.Objects;

public class CoordMatrix {
	static private class Coord {
		int row;
		int col;

		Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Coord coord = (Coord) o;
			return col == coord.col && row == coord.row;
		}

		@Override
		public int hashCode() {
			return Objects.hash(col, row);
		}
	}

	private HashMap<Coord, Double> elements = new HashMap<>();

	public double get(int row, int col) {
		return elements.getOrDefault(new Coord(row, col), 0.0);
	};

	public void set(int row, int col, double value) {
		elements.put(new Coord(row, col), value);
	}
}
