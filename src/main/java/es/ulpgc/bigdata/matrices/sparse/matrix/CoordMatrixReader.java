package es.ulpgc.bigdata.matrices.sparse.matrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class CoordMatrixReader {
	public static CoordMatrix read(BufferedReader reader) throws IOException {
		HashMap<Pair<Integer, Integer>, Double> map = null;

		String line;
		int rows = 0;
		int cols = 0;
		boolean hasReadSize = false;

		while((line = reader.readLine()) != null) {
			// skip comments
			if (line.startsWith("%")) {
				continue;
			}
			// read size if not read yet
			if (!hasReadSize) {
				String[] size = line.trim().split("\\s+");
				rows = Integer.parseInt(size[0]);
				cols = Integer.parseInt(size[1]);
				int numElems = Integer.parseInt(size[2]);
				map = new HashMap<>(numElems);
				hasReadSize = true;
				continue;
			}

			// read elements
			String[] elements = line.trim().split("\\s+");
			// turn to zero-based index
			int row = Integer.parseInt(elements[0]) - 1;
			int col = Integer.parseInt(elements[1]) - 1;
			double value = Double.parseDouble(elements[2]);
			map.put(new Pair<>(row, col), value);
		}
		return CoordMatrix.fromParts(rows, cols, map);
	}
}
