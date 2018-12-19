import java.util.ArrayList;
import java.util.List;

public class BTree {

	private final int M;
	private final int mid;
	private final int min;
	private BTreeNode root;

	public BTree(int M) {
		this.M = M;
		mid = M / 2;
		min = (int) Math.ceil(((double) M / 2)) - 1;
	}

	public void inorder() {
		inorder(root);
	}

	private void inorder(BTreeNode p) {
		if (!p.isLeaf()) {
			for (int i = 0; i < p.length(); i++) {
				inorder(p.getPointer(i));
				System.out.print(p.getKey(i) + " ");
			}
			inorder(p.getPointer(p.length()));
		} else {
			for (int i = 0; i < p.length(); i++)
				System.out.print(p.getKey(i) + " ");
		}
	}

	private int findIndex(BTreeNode p, int newKey) {
		int index = p.length();
		for (int i = 0; i < p.length(); i++) {
			if (newKey <= p.getKey(i)) {
				index = i;
				break;
			}
		}
		return index;
	}

	private BTreeNode splitBT(BTreeNode p) {
		BTreeNode parent;
		BTreeNode q = new BTreeNode();

		for (int i = mid + 1; i < M; i++) { // 중간값보다 큰값 새 노드에 담기
			q.addKey(p.removeKey(mid + 1));
		}

		int centerKey = p.removeKey(mid); // 중간값
		if (p.isRoot()) { // 루트노드일 때
			parent = new BTreeNode();
			int index = findIndex(parent, centerKey);
			parent.addKey(centerKey, index);
			parent.addPointer(p);
			parent.addPointer(q);
			p.setParent(parent);
			q.setParent(parent);
			root = parent;
		} else { // 내부노드 또는 리프일 때
			parent = p.getParent();
			int index = findIndex(parent, centerKey);
			parent.addKey(centerKey, index);
			parent.addPointer(q, index + 1);
			q.setParent(parent);
			if (parent.isOverflow()) { // 부모노드가 overflow일 때
				BTreeNode newParent = splitBT(parent);
				for (int i = mid; i < M; i++) { // 포인터 재배치 및 부모노드 재조정
					BTreeNode newChild = parent.removePointer(mid + 1);
					newChild.setParent(newParent);
					newParent.addPointer(newChild);
				}
			}
		}
		return q;
	}

	private void insertBT(BTreeNode p, int newKey) {
		int index = findIndex(p, newKey);
		if (p.isLeaf()) { // 리프노드일 때
			p.addKey(newKey, index);
			if (p.isOverflow()) { // 리프노드에 키를 삽입하여 오버플로우가 되었을 때
				splitBT(p);
			}
		} else { // 리프노드가 아닐 때
			insertBT(p.getPointer(index), newKey);
		}
	}

	public void insertBT(int newKey) {
		if (root == null) {
			root = new BTreeNode();
			root.addKey(newKey);
		} else {
			insertBT(root, newKey);
		}
	}

	private BTreeNode findNextNode(BTreeNode p, int index) { // 후행키를 갖고 있는 노드 찾기
		p = p.getPointer(index + 1);
		while (!p.isLeaf()) {
			p = p.getPointer(0);
		}
		return p;
	}

	private BTreeNode findSibling(BTreeNode p) { // 키를 최소값보다 많이 가진 형제노드 찾기
		BTreeNode sibling = null;
		BTreeNode parent = p.getParent();
		if (parent != null) {
			int index = parent.getPointerIndex(p);
			if (index - 1 >= 0 && parent.getPointer(index - 1).length() > min) // 왼쪽 형제
				sibling = parent.getPointer(index - 1);
			else if (index + 1 <= parent.length() && parent.getPointer(index + 1).length() > min) // 오른쪽 형제
				sibling = parent.getPointer(index + 1);
			return sibling;
		} else
			return null;
	}

	private void redistributeBT(BTreeNode sibling, BTreeNode p) { // 형제노드 이용해서 키 재분배
		BTreeNode parent = p.getParent();
		int sindex = parent.getPointerIndex(sibling);
		int pindex = parent.getPointerIndex(p);

		if (sindex < pindex) { // 형제노드가 왼쪽에 있을 때
			parent.addKey(sibling.removeKey(sibling.length() - 1), sindex);
			p.addKey(parent.removeKey(sindex + 1), 0);
			if (!sibling.isLeaf()) {
				BTreeNode newChild = sibling.removePointer(sibling.length() + 1);
				p.addPointer(newChild, 0);
				newChild.setParent(p);
			}
		} else { // 형제노드가 오른쪽에 있을 때
			parent.addKey(sibling.removeKey(0), sindex);
			p.addKey(parent.removeKey(sindex - 1));
			if (!sibling.isLeaf()) {
				BTreeNode newChild = sibling.removePointer(0);
				p.addPointer(newChild);
				newChild.setParent(p);
			}

		}
	}

	// M이 5 이상도 가능하게 수정
	private void mergeBT(BTreeNode p) {
		BTreeNode parent = p.getParent();
		BTreeNode mergeNode;
		int index = parent.getPointerIndex(p);
		parent.removePointer(index);
		if (index != 0) {
			int key = parent.removeKey(index - 1);
			mergeNode = parent.getPointer(index - 1);
			mergeNode.addKey(key);
			if (min > 1) { // M이 5 이상일 때 underflow인 노드의 키 옮기기
				for (int i = 0; i < min - 1; i++)
					mergeNode.addKey(p.removeKey(0));
			}

			if (!p.isLeaf()) { // underflow인 노드의 자식노드 옮기기
				for (int i = 0; i < min; i++) {
					BTreeNode newChild = p.removePointer(0);
					mergeNode.addPointer(newChild);
					newChild.setParent(mergeNode);
				}
			}
		} else {
			int key = parent.removeKey(0);
			mergeNode = parent.getPointer(0);
			mergeNode.addKey(key, 0);
			if (min > 1) { // M이 5 이상일 때 underflow인 노드의 키 옮기기
				for (int i = min - 1; i > 0; i--)
					mergeNode.addKey(p.removeKey(i - 1), 0);
			}

			if (!p.isLeaf()) { // underflow인 노드의 자식노드 옮기기
				for (int i = min; i > 0; i--) {
					BTreeNode newChild = p.removePointer(i - 1);
					mergeNode.addPointer(newChild, 0);
					newChild.setParent(mergeNode);
				}
			}
		}

		if (parent.isUnderflow()) { // 합병했는데 부모노드가 언더플로우가 되었을 때
			if (parent.getParent() != null) {
				BTreeNode sibling = findSibling(parent);
				if (sibling != null)
					redistributeBT(sibling, parent);
				else
					mergeBT(parent);
			} else {
				if (parent.length() == 0) { // root의 키가 하나도 없을 때
					mergeNode.setParent(null);
					root = mergeNode;
				}
			}
		}
	}

	private void deleteBT(BTreeNode p, int deleteKey) {
		int index = findIndex(p, deleteKey);

		if (index < p.length() && p.getKey(index) == deleteKey) {
			if (p.isLeaf()) { // 리프노드일 때
				p.removeKey(index);
				if (p.isUnderflow() && p != root) { // 삭제하여 언더플로우가 되었을 때
					BTreeNode sibling = findSibling(p);
					if (sibling != null)
						redistributeBT(sibling, p);
					else
						mergeBT(p);
				}
			} else { // 내부노드일 때
				BTreeNode nextNode = findNextNode(p, index);
				int temp = p.getKey(index);
				p.setKey(nextNode.getKey(0), index);
				nextNode.setKey(temp, 0);
				deleteBT(nextNode, deleteKey);
			}
		} else if (!p.isLeaf())
			deleteBT(p.getPointer(index), deleteKey);
	}

	public void deleteBT(int deleteKey) {
		if (root.isLeaf() && root.length() == 1 && root.getKey(0) == deleteKey)
			root = null;
		else
			deleteBT(root, deleteKey);
	}

	private class BTreeNode {

		private List<Integer> keys;
		private List<BTreeNode> pointers;
		private BTreeNode parent;

		public BTreeNode() {
			keys = new ArrayList<>(M); // 오버플로우 시 newKey를 저장하기 위해 리스트 길이 하나 늘림
			pointers = new ArrayList<>(M + 1); // 오버플로우 시 newPointer를 저장하기 위해 리스트 길이 하나 늘림
		}

		public int length() {
			return keys.size();
		}

		public int getKey(int index) {
			return keys.get(index);
		}

		public void setKey(int key, int index) {
			keys.set(index, key);
		}

		public void addKey(int key) {
			keys.add(key);
		}

		public void addKey(int key, int index) {
			keys.add(index, key);
		}

		public int removeKey(int index) {
			return keys.remove(index);
		}

		public BTreeNode getPointer(int index) {
			return pointers.get(index);
		}

		public void addPointer(BTreeNode pointer) {
			pointers.add(pointer);
		}

		public void addPointer(BTreeNode pointer, int index) {
			pointers.add(index, pointer);
		}

		public BTreeNode removePointer(int index) {
			return pointers.remove(index);
		}

		public int getPointerIndex(BTreeNode pointer) {
			return pointers.indexOf(pointer);
		}

		public BTreeNode getParent() {
			return parent;
		}

		public void setParent(BTreeNode parent) {
			this.parent = parent;
		}

		public boolean isRoot() {
			return parent == null;
		}

		public boolean isLeaf() {
			return pointers.isEmpty();
		}

		public boolean isOverflow() {
			return keys.size() >= M;
		}

		public boolean isUnderflow() {
			return keys.size() < min;
		}

	}

}
