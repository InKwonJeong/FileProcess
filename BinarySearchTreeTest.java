
public class BinarySearchTreeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinarySearchTree test = new BinarySearchTree();
		BinaryNode tree = new BinaryNode();
		int[] key = {8, 18, 11, 5, 15, 4, 9, 1, 7, 17, 6, 14, 10, 3, 19, 20};
		
		for(int k : key) {
			test.insertBST(tree, k); test.inorder(tree); System.out.println();
		}
		
		System.out.println("-----------------------------------------");
		
		for(int k : key) {
			test.deleteBST(tree, k); test.inorder(tree); System.out.println();
		}
		
		for(int k : key) {
			test.insertBST(tree, k);
		}		
		
		System.out.println("-----------------------------------------");
		
		for(int k = key.length;k>0;k--) {
			test.deleteBST(tree, key[k-1]); test.inorder(tree); System.out.println();
		}
		
	}

}
