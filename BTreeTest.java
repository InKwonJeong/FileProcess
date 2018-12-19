
public class BTreeTest {

	public static void main(String[] args) {

		BTree tree = new BTree(10);

		int[] insertKey = { 30, 20, 62, 110, 140, 15, 65, 136, 150, 120, 40, 132, 19, 128, 138, 100, 16, 145, 70, 42,
				69, 43, 26, 60, 130, 50, 18, 7, 36, 58, 22, 41, 59, 57, 54, 33, 75, 124, 122, 123 };

		int[] deleteKey = { 40, 132, 19, 128, 138, 100, 16, 145, 70, 42, 22, 41, 62, 110, 140, 15, 65, 124, 122, 123,
				30, 20, 59, 57, 54, 33, 75, 136, 150, 120, 69, 43, 26, 60, 130, 50, 18, 7, 36, 58 };

		for (int k : insertKey) {
			tree.insertBT(k);
			tree.inorder();
			System.out.println();
		}

		System.out.println(
				"--------------------------------------------------------------------------"
				+ "------------------------------------------------------------------------");

		for (int k : deleteKey) {
			tree.inorder();
			System.out.println();
			tree.deleteBT(k);
		}

	}

}
