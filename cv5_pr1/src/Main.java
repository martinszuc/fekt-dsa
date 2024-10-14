public class Main {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        int[] elements = {1, 2, 4, 5, 7, 8, 9};
        for (int elem : elements) {
            bst.insert(elem);
        }

        System.out.print("In-order Traversal: ");
        bst.inOrder();
        System.out.print("Pre-order Traversal: ");
        bst.preOrder();
        System.out.print("Post-order Traversal: ");
        bst.postOrder();
        System.out.print("Reverse In-order Traversal: ");
        bst.reverseOrder();

        System.out.println();
        int searchValue = 5;
        System.out.println("Contains " + searchValue + ": " + bst.contains(searchValue));
        searchValue = 3;
        System.out.println("Contains " + searchValue + ": " + bst.contains(searchValue));

        System.out.println();
        bst.printLeaves();

        System.out.println();
        TreeNode deepestNode = bst.findDeepestNode();
        if (deepestNode != null)
            System.out.println("Deepest Node: " + deepestNode.value);

        System.out.println();
        TreeNode head = bst.toDoublyLinkedList();
        bst.printDoublyLinkedList(head);
    }
}