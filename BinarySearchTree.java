
public class BinarySearchTree {

	// 키 값이 가장 큰 노드 탐색
	public BinaryNode maxNode(BinaryNode tree) {
		BinaryNode p = tree;

		if (p.getRight() != null) {
			p = p.getRight();
			return maxNode(p);
		} else
			return p;
	}

	// 키 값이 가장 작은 노드 탐색
	public BinaryNode minNode(BinaryNode tree) {
		BinaryNode p = tree;

		if (p.getLeft() != null) {
			p = p.getLeft();
			return minNode(p);
		} else
			return p;
	}

	// 노드 개수 구하는 연산
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

	// 노드 높이 구하는 연산
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

	// 탐색 연산
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

	// 삽입 연산
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

	// 삭제 연산
	public void deleteBST(BinaryNode tree, int deleteKey) {
		BinaryNode p = searchBST(tree, deleteKey);
		BinaryNode q = p.getParent();
		BinaryNode r;
		String flag;

		if (p == null)
			return;

		// 삭제할 노드의 차수가 0일 경우
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
		// 삭제할 노드의 차수가 1인 경우
		else if (p.getLeft() == null || p.getRight() == null) {
			// 부모노드가 있는 경우
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
				// 루트노드인 경우
			} else {
				if (p.getLeft() != null)
					r = p.getLeft();
				else
					r = p.getRight();

				p.setKey(r.getKey());
				deleteBST(r, r.getKey());
			}
		}
		// 삭제할 노드의 차수가 2인 경우
		else if (p.getLeft() != null && p.getRight() != null) {
			// 왼쪽 서브트리 높이가 더 높을 때
			if (height(p.getLeft()) > height(p.getRight())) {
				r = maxNode(p.getLeft());
				flag = "LEFT";
			}
			// 오른쪽 서브트리 높이가 더 높을 때
			else if (height(p.getLeft()) < height(p.getRight())) {
				r = minNode(p.getRight());
				flag = "Right";
			}
			// 높이가 같을 때
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

	// inorder 출력
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
