
public class BinarySearchTree {

	// Ű ���� ���� ū ��� Ž��
	public BinaryNode maxNode(BinaryNode tree) {
		BinaryNode p = tree;

		if (p.getRight() != null) {
			p = p.getRight();
			return maxNode(p);
		} else
			return p;
	}

	// Ű ���� ���� ���� ��� Ž��
	public BinaryNode minNode(BinaryNode tree) {
		BinaryNode p = tree;

		if (p.getLeft() != null) {
			p = p.getLeft();
			return minNode(p);
		} else
			return p;
	}

	// ��� ���� ���ϴ� ����
	public int noNodes(BinaryNode tree) {
		int count = 1;

		if (tree == null)
			return 0;

		if (tree.getLeft() != null && tree.getRight() == null)
			count += noNodes(tree.getLeft());
		else if (tree.getLeft() == null && tree.getRight() != null) {
			count += noNodes(tree.getRight());
		} else if (tree.getLeft() != null && tree.getRight() != null)
			count += noNodes(tree.getLeft()) + noNodes(tree.getRight());

		return count;
	}

	// ��� ���� ���ϴ� ����
	public int height(BinaryNode tree) {
		int height = 1;
		int left = 0;
		int right = 0;

		if (tree == null)
			return 0;

		if (tree.getLeft() != null)
			left = height(tree.getLeft());
		if (tree.getRight() != null)
			right = height(tree.getRight());

		if (left > right)
			height += left;
		else
			height += right;

		return height;
	}

	// Ž�� ����
	public BinaryNode searchBST(BinaryNode tree, int key) {
		BinaryNode p = tree;
		if (p == null)
			return null;
		else if (p.getKey() == key)
			return p;
		else if (p.getKey() < key)
			return searchBST(p.getRight(), key);
		else
			return searchBST(p.getLeft(), key);
	}

	// ���� ����
	public void insertBST(BinaryNode tree, int newKey) {
		BinaryNode p = tree;
		BinaryNode q = null;
		while (p != null) {
			if (newKey == p.getKey())
				return;
			q = p;
			if (newKey < p.getKey())
				p = p.getLeft();
			else
				p = p.getRight();
		}

		BinaryNode newNode = new BinaryNode(newKey);

		if (tree.getKey() == 0)
			tree.setKey(newKey);
		else if (newKey < q.getKey()) {
			newNode.setParent(q);
			q.setLeft(newNode);
		} else {
			newNode.setParent(q);
			q.setRight(newNode);
		}
	}

	// ���� ����
	public void deleteBST(BinaryNode tree, int deleteKey) {
		BinaryNode p = searchBST(tree, deleteKey);
		BinaryNode q = p.getParent();
		BinaryNode r;
		String flag;

		if (p == null)
			return;

		// ������ ����� ������ 0�� ���
		if (p.getLeft() == null && p.getRight() == null) {
			if (q != null) {
				if (q.getLeft() == p)
					q.setLeft(null);
				else
					q.setRight(null);
			}
			else {
				p.setKey(0);
			}
		}
		// ������ ����� ������ 1�� ���
		else if (p.getLeft() == null || p.getRight() == null) {
			// �θ��尡 �ִ� ���
			if (q != null) {
				if (p.getLeft() != null) {
					if (q.getLeft() == p) {
						r = p.getLeft();
						r.setParent(q);
						q.setLeft(r);
					} else {
						r = p.getLeft();
						r.setParent(q);
						q.setRight(r);
					}
				} else {
					if (q.getLeft() == p) {
						r = p.getRight();
						r.setParent(q);
						q.setLeft(r);
					} else {
						r = p.getRight();
						r.setParent(q);
						q.setRight(r);
					}
				}
				// ��Ʈ����� ���
			} else {
				if (p.getLeft() != null)
					r = p.getLeft();
				else
					r = p.getRight();

				p.setKey(r.getKey());
				deleteBST(r, r.getKey());
			}
		}
		// ������ ����� ������ 2�� ���
		else if (p.getLeft() != null && p.getRight() != null) {
			// ���� ����Ʈ�� ���̰� �� ���� ��
			if (height(p.getLeft()) > height(p.getRight())) {
				r = maxNode(p.getLeft());
				flag = "LEFT";
			}
			// ������ ����Ʈ�� ���̰� �� ���� ��
			else if (height(p.getLeft()) < height(p.getRight())) {
				r = minNode(p.getRight());
				flag = "Right";
			}
			// ���̰� ���� ��
			else {
				if (noNodes(p.getLeft()) >= noNodes(p.getRight())) {
					r = maxNode(p.getLeft());
					flag = "LEFT";
				} else {
					r = minNode(p.getRight());
					flag = "Right";
				}
			}
			p.setKey(r.getKey());
			if (flag == "LEFT")
				deleteBST(p.getLeft(), r.getKey());
			else
				deleteBST(p.getRight(), r.getKey());
		}

	}

	// inorder ���
	public void inorder(BinaryNode tree) {
		BinaryNode p = tree;

		if (p != null) {
			if (p.getLeft() != null)
				inorder(p.getLeft());
			if(p.getKey() != 0)
				System.out.print(p.getKey() + " ");
			if (p.getRight() != null)
				inorder(p.getRight());
		}
	}

	public void preorder(BinaryNode tree) {
		System.out.print(tree.getKey() + " ");
		if (tree.getLeft() != null)
			preorder(tree.getLeft());
		if (tree.getRight() != null)
			preorder(tree.getRight());
	}

}
