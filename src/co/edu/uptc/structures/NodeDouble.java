package co.edu.uptc.structures;

public class NodeDouble<E> {
	private NodeDouble<E> left;
	private E data;
	private NodeDouble<E> right;
	private int factorEquilibrium;

	public NodeDouble(E data) {
		this.data = data;
		this.factorEquilibrium = 0;
	}

	public NodeDouble<E> getLeft() {
		return left;
	}

	public void setLeft(NodeDouble<E> left) {
		this.left = left;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public NodeDouble<E> getRight() {
		return right;
	}

	public void setRight(NodeDouble<E> right) {
		this.right = right;
	}

	public int getFactorEquilibrium() {
		return factorEquilibrium;
	}

	public void setFactorEquilibrium(int factorEquilibrium) {
		this.factorEquilibrium = factorEquilibrium;
	}
}
