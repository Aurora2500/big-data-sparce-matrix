package es.ulpgc.bigdata.matrices.sparse.matrix;

import java.util.Objects;

public class Pair<TLeft, TRight> {
	private final TLeft left;
	private final TRight right;

	public Pair(TLeft left, TRight right) {
		this.left = left;
		this.right = right;
	}

	public TLeft left() {
		return left;
	}

	public TRight right() {
		return right;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pair<?, ?> pair = (Pair<?, ?>) o;
		return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
	}

	@Override
	public int hashCode() {
		return Objects.hash(left, right);
	}

	@Override
	public String toString() {
		return "(" + left + ", " + right + ')';
	}
}
