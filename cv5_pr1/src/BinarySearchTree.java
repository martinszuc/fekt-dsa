import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree {
    private TreeNode root;

    public BinarySearchTree() {
        root = null;
    }

    // insert a value into the BST
    public void insert(int value) {
        root = insertRec(root, value);
    }

    /**
     * Recursive helper method to insert a new value into the BST.
     *
     * Compares the value to be inserted with the current node's value
     * and recursively traverses the left or right subtree accordingly. If the current
     * node is {@code null}, it creates a new {@code TreeNode} with the given value.
     *
     * @param root  The current node in the BST.
     * @param value The integer value to be inserted.
     * @return The updated node after insertion.
     */    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode(value);
            return root;
        }

        if (value < root.value)
            root.left = insertRec(root.left, value);
        else if (value > root.value)
            root.right = insertRec(root.right, value);
        // Duplicate values are not inserted in the BST
        return root;
    }

    public boolean contains(int value) {
        return containsRec(root, value);
    }

    /**
     * Recursive helper method to check if a value exists in the BST.
     *
     * Compares the target value with the current node's value and
     * recursively searches the left or right subtree based on the comparison.
     *
     * @param root  The current node in the BST.
     * @param value The integer value to search for.
     * @return {@code true} if the value is found, {@code false} otherwise.
     */
    private boolean containsRec(TreeNode root, int value) {
        if (root == null)
            return false;
        if (value == root.value)
            return true;
        return value < root.value
                ? containsRec(root.left, value)
                : containsRec(root.right, value);
    }

    // In-order traversal
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(TreeNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.print(root.value + " ");
            inOrderRec(root.right);
        }
    }

    // Pre-order traversal
    public void preOrder() {
        preOrderRec(root);
        System.out.println();
    }

    private void preOrderRec(TreeNode root) {
        if (root != null) {
            System.out.print(root.value + " ");
            preOrderRec(root.left);
            preOrderRec(root.right);
        }
    }

    // Post-order traversal
    public void postOrder() {
        postOrderRec(root);
        System.out.println();
    }

    private void postOrderRec(TreeNode root) {
        if (root != null) {
            postOrderRec(root.left);
            postOrderRec(root.right);
            System.out.print(root.value + " ");
        }
    }

    // Reverse in-order traversal
    public void reverseOrder() {
        reverseOrderRec(root);
        System.out.println();
    }

    private void reverseOrderRec(TreeNode root) {
        if (root != null) {
            reverseOrderRec(root.right);
            System.out.print(root.value + " ");
            reverseOrderRec(root.left);
        }
    }

    public void printLeaves() {
        System.out.print("Leaves: ");
        printLeavesRec(root);
        System.out.println();
    }

    private void printLeavesRec(TreeNode root) {
        if (root != null) {
            if (root.left == null && root.right == null)
                System.out.print(root.value + " ");
            printLeavesRec(root.left);
            printLeavesRec(root.right);
        }
    }

    /**
     * Finds the node with the greatest depth (deepest node) in the BST.
     *
     * This method performs a level-order traversal (Breadth-First Search) using a
     * queue to visit nodes level by level. The last node visited during this traversal
     * is the deepest node in the tree.
     *
     * @return The deepest {@code TreeNode} in the BST, or {@code null} if the tree is empty.
     */
    public TreeNode findDeepestNode() {
        if (root == null)
            return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode current = null;
        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.left != null)
                queue.add(current.left);
            if (current.right != null)
                queue.add(current.right);
        }
        return current;
    }

    /**
     * Converts the Binary Search Tree (BST) into a Doubly Linked List (DLL).
     *
     * Performs an in-order traversal of the BST. As it traverses the tree, it
     * rearranges the left and right pointers of each node to act as previous and
     * next pointers in the DLL. No new memory is allocated; instead, the existing
     * node references are modified to form the doubly linked list.
     *
     * @return The head node of the resulting Doubly Linked List. Returns null if the BST is empty.
     */
    public TreeNode toDoublyLinkedList() {
        if (root == null)
            return null;
        TreeNode[] head = new TreeNode[1];
        TreeNode[] prev = new TreeNode[1];
        toDoublyLinkedListRec(root, head, prev);
        return head[0];
    }

    private void toDoublyLinkedListRec(TreeNode current, TreeNode[] head, TreeNode[] prev) {
        if (current == null)
            return;
        toDoublyLinkedListRec(current.left, head, prev);
        if (prev[0] == null) {
            head[0] = current;
        } else {
            prev[0].right = current;
            current.left = prev[0];
        }
        prev[0] = current;
        toDoublyLinkedListRec(current.right, head, prev);
    }

    public void printDoublyLinkedList(TreeNode head) {
        System.out.print("Doubly Linked List: ");
        TreeNode current = head;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.right;
        }
        System.out.println();
    }
}