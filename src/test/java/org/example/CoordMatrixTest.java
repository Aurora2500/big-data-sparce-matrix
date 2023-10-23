package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordMatrixTest {
	@Test
	void getAndSetTest() {
		CoordMatrix mat = new CoordMatrix();
		Double v1 = mat.get(3, 4);
		assertEquals(v1, 0.0);
		mat.set(3, 4, 5.0);
		double v2 = mat.get(3, 4);
		assertEquals(v2, 5.0);
	}
}