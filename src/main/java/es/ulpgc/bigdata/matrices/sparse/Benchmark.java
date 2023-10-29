package es.ulpgc.bigdata.matrices.sparse;

import es.ulpgc.bigdata.matrices.sparse.matrix.CCSMatrix;
import es.ulpgc.bigdata.matrices.sparse.matrix.CoordMatrix;
import es.ulpgc.bigdata.matrices.sparse.matrix.IndexedCoordMatrix;

import java.util.List;

public class Benchmark {
	private static final List<Double> TEST_DENSITIES = List.of(0.002, 0.01, 0.05);
	private static final List<Integer> TEST_SIZES = List.of(1024, 2048);
	public void run() {

		System.out.println("--- Indexed Coord Matrix ---");
		System.out.println("Density,Size,Time (s)");
		for (double density : TEST_DENSITIES) {
			for (int size : TEST_SIZES) {
				IndexedCoordMatrix left = new IndexedCoordMatrix(randomMatrix(size, density));
				IndexedCoordMatrix right = new IndexedCoordMatrix(randomMatrix(size, density));

				long start = System.currentTimeMillis();
				IndexedCoordMatrix result = left.multiply(right);
				long end = System.currentTimeMillis();

				System.out.println(density + "," + size + "," + (end - start) / 1000.0);
			}
		}

		System.out.println();
		System.out.println("--- Coord Matrix ---");
		System.out.println("Density,Size,Time (s)");
		for (double density : TEST_DENSITIES) {
			for (int size : TEST_SIZES) {
				CoordMatrix left = randomMatrix(size, density);
				CoordMatrix right = randomMatrix(size, density);

				long start = System.currentTimeMillis();
				CoordMatrix result = left.multiply(right);
				long end = System.currentTimeMillis();

				System.out.println(density + "," + size + "," + (end - start) / 1000.0);
			}
		}

		return;

		// TODO: fix CSS matrix multiplication.

		/*
		System.out.println();
		System.out.println("--- CCS Matrix ---");
		System.out.println("Density, Size, Time (s)");
		for (double density : TEST_DENSITIES) {
			for (int size : TEST_SIZES) {
				CCSMatrix left = new CCSMatrix(randomMatrix(size, density));
				CCSMatrix right = new CCSMatrix(randomMatrix(size, density));

				long start = System.currentTimeMillis();
				CCSMatrix result = left.multiply(right);
				long end = System.currentTimeMillis();

				System.out.println(density + ", " + size + ", " + (end - start) / 1000.0);
			}
		}
		 */
	}

	private CoordMatrix randomMatrix(int size, double density) {
		CoordMatrix matrix = new CoordMatrix(size, size);
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (Math.random() < density) {
					matrix.set(row, col, Math.random());
				}
			}
		}
		return matrix;
	}
}
