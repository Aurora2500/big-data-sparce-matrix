package es.ulpgc.bigdata.matrices.sparse;

import es.ulpgc.bigdata.matrices.sparse.matrix.CoordMatrix;
import es.ulpgc.bigdata.matrices.sparse.matrix.IndexedCoordMatrix;
import es.ulpgc.bigdata.matrices.sparse.proptest.RandomCoordMatrix;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class IndexedCoordMatrixTest {
	private static final int ITERS = 64;
	private static final double EPSILON = 1e-6;
	@Test
	void IndexedCreation() {
		CoordMatrix matrixUnindexed = new CoordMatrix(3, 3 );
		matrixUnindexed.set(0, 0, 2);
		matrixUnindexed.set(0, 1, 3);
		matrixUnindexed.set(0, 2, 4);

		IndexedCoordMatrix matIndexed = new IndexedCoordMatrix(matrixUnindexed);
		assertEquals(matIndexed.get(0, 0), 2);
		assertEquals(matIndexed.get(0, 1), 3);
		assertEquals(matIndexed.get(0, 2), 4);

		TreeMap<Integer, Double> row = matIndexed.getRow(0);
		assertEquals(row.size(), 3);
		TreeMap<Integer, Double> col = matIndexed.getCol(0);
		assertEquals(col.size(), 1);
	}

	@Test
	void matmulAssoc() {
		for(int iter = 0; iter < ITERS; iter++) {
			IndexedCoordMatrix mat1 = new IndexedCoordMatrix(RandomCoordMatrix.newRandom(100, 100, 0.5));
			IndexedCoordMatrix mat2 = new IndexedCoordMatrix(RandomCoordMatrix.newRandom(100, 100, 0.5));
			IndexedCoordMatrix mat3 = new IndexedCoordMatrix(RandomCoordMatrix.newRandom(100, 100, 0.5));

			IndexedCoordMatrix leftAssoc = mat1.multiply(mat2).multiply(mat3);
			IndexedCoordMatrix rightAssoc = mat1.multiply(mat2.multiply(mat3));

			assertTrue(leftAssoc.epsilonEquals(rightAssoc, EPSILON));
		}
	}


	@Test
	void matmulIdent() {
		IndexedCoordMatrix eye = new IndexedCoordMatrix(RandomCoordMatrix.eye(100));
		for(int iter = 0; iter < ITERS; iter++) {
			IndexedCoordMatrix mat = new IndexedCoordMatrix(RandomCoordMatrix.newRandom(100, 100, 0.5));

			IndexedCoordMatrix leftIdent = eye.multiply(mat);
			IndexedCoordMatrix rightIdent = mat.multiply(eye);

			assertTrue(leftIdent.epsilonEquals(mat, EPSILON));
			assertTrue(rightIdent.epsilonEquals(mat, EPSILON));
		}
	}
}
