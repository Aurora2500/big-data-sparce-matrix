package es.ulpgc.bigdata.matrices.sparse;

import es.ulpgc.bigdata.matrices.sparse.matrix.CoordMatrix;
import es.ulpgc.bigdata.matrices.sparse.matrix.CoordMatrixReader;
import es.ulpgc.bigdata.matrices.sparse.proptest.RandomCoordMatrix;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class CoordMatrixTest {
	private static final int ITERS = 64;
	private static final double EPSILON = 1e-5;
	@Test
	void getAndSetTest() {
		CoordMatrix mat = new CoordMatrix(8, 8);
		Double v1 = mat.get(3, 4);
		assertEquals(v1, 0.0);
		mat.set(3, 4, 5.0);
		double v2 = mat.get(3, 4);
		assertEquals(v2, 5.0);
	}

	@Test
	void reader() throws IOException {
		String testMatrix = "3 3 3\n1 1 1.0\n2 2 2.0\n3 3 3.0\n";
		CoordMatrix mat = CoordMatrixReader.read(new BufferedReader(new StringReader(testMatrix)));
		assertEquals(mat.rows(), 3);
		assertEquals(mat.cols(), 3);
		assertEquals(mat.get(0, 0), 1.0);
		assertEquals(mat.get(1, 1), 2.0);
		assertEquals(mat.get(2, 2), 3.0);
	}

	@Test
	void matmulAssoc() {
		for (int iter = 0; iter < ITERS; iter++) {
			CoordMatrix mat1 = RandomCoordMatrix.newRandom(100, 100, 0.5);
			CoordMatrix mat2 = RandomCoordMatrix.newRandom(100, 100, 0.5);
			CoordMatrix mat3 = RandomCoordMatrix.newRandom(100, 100, 0.5);

			CoordMatrix leftAssoc = mat1.multiply(mat2).multiply(mat3);
			CoordMatrix rightAssoc = mat1.multiply(mat2.multiply(mat3));

			assertTrue(leftAssoc.epsilonEquals(rightAssoc, EPSILON));

		}
	}

	@Test
	void matmulIdent() {
		CoordMatrix eye = RandomCoordMatrix.eye(100);
		for(int iter = 0; iter < ITERS; iter++) {
			CoordMatrix mat = RandomCoordMatrix.newRandom(100, 100, 0.5);

			CoordMatrix leftIdent = eye.multiply(mat);
			CoordMatrix rightIdent = mat.multiply(eye);

			assertTrue(leftIdent.epsilonEquals(mat, EPSILON));
			assertTrue(rightIdent.epsilonEquals(mat, EPSILON));
		}
	}
}