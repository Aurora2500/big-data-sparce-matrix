package es.ulpgc.bigdata.matrices.sparse.matrix;

import java.util.Objects;

public class Triplet<TLeft, TMid, TRight> {
	private final TLeft left;
	private final TMid mid;
	private final TRight right;

	public Triplet(TLeft left, TMid mid, TRight right) {
		this.left = left;
		this.mid = mid;
		this.right = right;
	}

	public TLeft left() {
		return left;
	}

	public TMid mid() {
		return mid;
	}

	public TRight right() {
		return right;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
		return Objects.equals(left, triplet.left) && Objects.equals(mid, triplet.mid) && Objects.equals(right, triplet.right);
	}

	@Override
	public int hashCode() {
		return Objects.hash(left, mid, right);
	}

	public Pair<TLeft, TMid> excludeRight() {
		return new Pair<>(left, mid);
	}

	public Pair<TMid, TRight> excludeLeft() {
		return new Pair<>(mid, right);
	}

	public Pair<TLeft, TRight> excludeMid() {
		return new Pair<>(left, right);
	}
}
