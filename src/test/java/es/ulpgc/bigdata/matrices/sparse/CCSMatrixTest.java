package es.ulpgc.bigdata.matrices.sparse;

import es.ulpgc.bigdata.matrices.sparse.matrix.CCSMatrix;
import es.ulpgc.bigdata.matrices.sparse.matrix.CoordMatrix;
import es.ulpgc.bigdata.matrices.sparse.proptest.RandomCoordMatrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CCSMatrixTest {
	private static final int ITERS = 64;
	private static final double EPSILON = 1e-6;
	@Test
	void creation() throws Exception {
		CoordMatrix matrix = new CoordMatrix(3, 3);
		matrix.set(0, 0, 2);
		matrix.set(1, 1, 3);
		matrix.set(2, 2, 4);

		CCSMatrix ccsMatrix = new CCSMatrix(matrix);

		assertEquals(ccsMatrix.get(0, 0), 2);
		assertEquals(ccsMatrix.get(1, 1), 3);
		assertEquals(ccsMatrix.get(2, 2), 4);
	}

	@Test
	void matmulAssoc() throws Exception {
		for (int iter = 0; iter < ITERS; iter++) {
			CCSMatrix mat1 = new CCSMatrix(RandomCoordMatrix.newRandom(100, 100, 0.5));
			CCSMatrix mat2 = new CCSMatrix(RandomCoordMatrix.newRandom(100, 100, 0.5));
			CCSMatrix mat3 = new CCSMatrix(RandomCoordMatrix.newRandom(100, 100, 0.5));

			CCSMatrix leftAssoc = mat1.multiply(mat2).multiply(mat3);
			CCSMatrix rightAssoc = mat1.multiply(mat2.multiply(mat3));

			assertTrue(leftAssoc.epsilonEquals(rightAssoc, EPSILON));
		}
	}
}