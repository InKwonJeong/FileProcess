
public class BinaryNode {

	private int key;
	private BinaryNode left;
	private BinaryNode right;
	private BinaryNode parent;
	
	public BinaryNode() {}
	public BinaryNode(int key) {
		this.setKey(key);
	}

	public BinaryNode getParent() {
		return parent;
	}

	public void setParent(BinaryNode parent) {
		this.parent = parent;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public BinaryNode getLeft() {
		return left;
	}

	public void setLeft(BinaryNode left) {
		this.left = left;
	}

	public BinaryNode getRight() {
		return right;
	}

	public void setRight(BinaryNode right) {
		this.right = right;
	}
}
