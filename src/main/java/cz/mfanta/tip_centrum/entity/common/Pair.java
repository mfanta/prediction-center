package cz.mfanta.tip_centrum.entity.common;

public class Pair<A, B> {

	private A first;
	private B second;

	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * @return the first
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	public B getSecond() {
		return second;
	}

}
